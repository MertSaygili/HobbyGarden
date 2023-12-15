package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserAlreadyExist;
import hobby_garden.hobby_garden_server.common.mapper.PasswordMapper;
import hobby_garden.hobby_garden_server.user.dto.request.CreateUser;
import hobby_garden.hobby_garden_server.user.dto.response.UserResponse;
import hobby_garden.hobby_garden_server.user.entity.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final PasswordMapper passwordMapper;

    @Override
    public BaseResponse<UserResponse> createUser(CreateUser createUser) {
        //* check if user with this username already exists
        if(userRepository.findByUsername(createUser.getUsername()) != null){
            throw new UserAlreadyExist(Strings.usernameInUse);
        }

        //* create new user entity model
        User user = new User(
                null,
                createUser.getUsername(),
                createUser.getFirstNameLastName(),
                passwordMapper.encode(createUser.getPassword()),
                createUser.getEmail(),
                createUser.getHobbies(),
                createUser.getCreatedAt()
        );

        try{
            // save user to db
            User newUser = userRepository.save(user);

            // create response
            UserResponse response = new UserResponse(
                    newUser.getUserId(),
                    newUser.getEmail(),
                    newUser.getUsername(),
                    newUser.getUsername(),
                    newUser.getHobbies(),
                    newUser.getCreatedAt().toString()
            );

            return new BaseResponse<>(true, Strings.userCreated, response);
        } catch (Exception e) {
            throw new UnknownException(Strings.unknownExceptionWhileCreatingUser);
        }
    }
}
