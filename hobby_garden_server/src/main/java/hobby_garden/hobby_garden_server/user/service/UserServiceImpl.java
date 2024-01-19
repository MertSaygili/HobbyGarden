package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.enums.Roles;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserAlreadyExist;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.service.HobbyService;
import hobby_garden.hobby_garden_server.user.dto.request.ForgotPasswordRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.request.UpdatePasswordRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HobbyService hobbyService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordMapper;
    private final JWTService jwtService;

    @Override
    public BaseResponse<SignInResponse> signIn(SignInRequest signInRequest) {
        //* check if user exist
        if(userRepository.findByUsername(signInRequest.getUsername()).isEmpty()) {
            return new BaseResponse<>(false, Strings.userNotFound, null);
        }

        //* get user from db
        User user = userRepository.findByUsername(signInRequest.getUsername()).get();

        //* authenticate user, generate jwt and refresh token
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        ));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        //* create response body and return
        SignInResponse response = new SignInResponse(
                jwt,
                user.getUserId(),
                refreshToken
        );

        return new BaseResponse<>(true, Strings.userSignedIn, response);
    }

    @Override
    public BaseResponse<Object> signUp(SignUpRequest signUpRequest) {
        //* check if user already exist
        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExist(Strings.usernameInUse);
        }

        //* create new user
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordMapper.encode(signUpRequest.getPassword()));
        user.setFirstNameLastName(signUpRequest.getFirstNameLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Roles.USER);
        user.setHobbies(new ArrayList<>(Collections.emptyList()));

        //* user hobby relationship integration
        List<Hobby> userHobbies = new ArrayList<>(Collections.emptyList());
        for (String hobbyName : signUpRequest.getHobbies()) {
            userHobbies.add(hobbyService.createHobbyToUser(hobbyName));
        }
        user.setHobbies(userHobbies);

        //* save user to db
        try {
            userRepository.save(user);
            return new BaseResponse<>(true, Strings.userCreated, null);
        } catch (Exception e) {
            throw new UnknownException(Strings.unknownExceptionWhileCreatingUser + " " + e.getMessage());
        }
    }

    @Override
    public BaseResponse<String> updatePassword(String token, UpdatePasswordRequest updatePasswordRequest) {
        //* get user from db
        User user = getUserNameByToken(token);

        //* check if old password is correct
        if(passwordMapper.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordMapper.encode(updatePasswordRequest.getNewPassword()));
            userRepository.save(user);
            try{
                userRepository.save(user);
                return new BaseResponse<>(true, Strings.userUpdated, null);

            } catch (Exception e) {
                throw new UnknownException(Strings.unknownExceptionWhileUpdatingUser + " " + e.getMessage());
            }
        }
        else{
            return new BaseResponse<>(false, Strings.wrongUserPassword, null);
        }
    }

    @Override
    public BaseResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<User> user = userRepository.findByUsername(forgotPasswordRequest.getUsername());

        //* check user is existed or not, username is unique
        if(user.isPresent()) {
            User userInfo = user.get();
            //* check email is correct, then send [email] with new password
            if(userInfo.getEmail().equals(forgotPasswordRequest.getEmail())) {
                userInfo.setPassword(passwordMapper.encode(forgotPasswordRequest.getNewPassword()));
                userRepository.save(userInfo);
                try{
                    userRepository.save(userInfo);
                    return new BaseResponse<>(true, Strings.passwordChanged, null);

                } catch (Exception e) {
                    throw new UnknownException(Strings.unknownExceptionWhileUpdatingUser + " " + e.getMessage());
                }
            }
            else{
                return new BaseResponse<>(false, Strings.userNotFound, null);
            }
        }
        else{
            return new BaseResponse<>(false, Strings.userNotFound, null);
        }

    }


    private User getUserNameByToken(String token) {
        String username = jwtService.extractUserNameWithBearer(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }
}
