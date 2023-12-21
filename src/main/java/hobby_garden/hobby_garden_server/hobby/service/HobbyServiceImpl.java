package hobby_garden.hobby_garden_server.hobby.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.BadHobbyNameFormat;
import hobby_garden.hobby_garden_server.common.exception.exceptions.HobbyNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserNotFoundException;
import hobby_garden.hobby_garden_server.hobby.dto.request.DeleteHobbyRequest;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import hobby_garden.hobby_garden_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService{

    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;

    private final JWTService jwtService;

    @Override
    public Hobby createHobbyToUser(String hobbyName) {

        //* check if hobby name is valid
        if(hobbyName == null || hobbyName.isEmpty()){
            throw new BadHobbyNameFormat(Strings.hobbyNameIsEmpty);
        }

        //* check if hobby is already in db, if already created it then return it
        Hobby hobby = hobbyRepository.findByName(hobbyName).orElse(null);
        if(hobby != null){
            return hobby;
        }

        //* create new hobby, if hobby is not in db
        hobby = new Hobby();
        hobby.setName(hobbyName);

        try{
            //* save hobby to db
            return hobbyRepository.save(hobby);
        }
        catch (Exception e){
            throw new BadHobbyNameFormat(Strings.errorOccurWhileCreatingHobby);
        }
    }

    @Override
    public BaseResponse<String> deleteHobbyFromUser(DeleteHobbyRequest deleteHobbyRequest) {
        //* get user from user repository by user token, jwtService.extractUserName() returns username from token
        String username = jwtService.extractUserName(deleteHobbyRequest.getUserToken());
        System.out.println("username: " + username);
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            throw new UserNotFoundException(Strings.userNotFound);
        }

        //* check if user has hobby
        Optional<Hobby> hobby = hobbyRepository.findById(deleteHobbyRequest.getHobbyId());

        //* if user has no hobby, throw exception
        if(hobby.isEmpty()){
            throw new HobbyNotFoundException(Strings.hobbyNotFound);
        }

        //* if user has hobby, delete it then save user
        user.getHobbies().remove(hobby.get());
        userRepository.save(user);

        //* return response
        return new BaseResponse<>(true, Strings.hobbyDeleted, null);
    }
}
