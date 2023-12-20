package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.enums.Roles;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserAlreadyExist;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;
import hobby_garden.hobby_garden_server.user.entity.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordMapper;
    private final JWTService jwtService;

    @Override
    public BaseResponse<SignInResponse> signIn(SignInRequest signInRequest) {
        if(userRepository.findByUsername(signInRequest.getUsername()).isEmpty()) {
            throw new UsernameNotFoundException(Strings.userNotFound);
        }

        User user = userRepository.findByUsername(signInRequest.getUsername()).get();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        ));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        SignInResponse response = new SignInResponse(
                jwt,
                user.getUserId(),
                refreshToken
        );

        return new BaseResponse<>(true, Strings.userSignedIn, response);
    }

    @Override
    public BaseResponse<Object> signUp(SignUpRequest signUpRequest) {
        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExist(Strings.usernameInUse);
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordMapper.encode(signUpRequest.getPassword()));
        user.setFirstNameLastName(signUpRequest.getFirstNameLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setHobbies(signUpRequest.getHobbies());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Roles.USER);


        try {
            userRepository.save(user);
            return new BaseResponse<>(true, Strings.userCreated, null);
        } catch (Exception e) {
            throw new UnknownException(Strings.unknownExceptionWhileCreatingUser + " " + e.getMessage());
        }

    }
}
