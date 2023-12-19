package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.enums.Roles;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserAlreadyExist;
import hobby_garden.hobby_garden_server.common.mapper.PasswordMapper;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;
import hobby_garden.hobby_garden_server.user.dto.response.UserResponse;
import hobby_garden.hobby_garden_server.user.entity.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordMapper passwordMapper;
    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }

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

        User user = new User(
                null,
                signUpRequest.getUsername(),
                signUpRequest.getFirstNameLastName(),
                passwordMapper.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),
                signUpRequest.getHobbies(),
                LocalDateTime.now(),
                Roles.USER
        );

        try {
            userRepository.save(user);
            return new BaseResponse<>(true, Strings.userCreated, null);
        } catch (Exception e) {
            throw new UnknownException(Strings.unknownExceptionWhileCreatingUser + " " + e.getMessage());
        }

    }
}
