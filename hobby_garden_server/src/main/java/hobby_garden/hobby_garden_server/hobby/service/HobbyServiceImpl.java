package hobby_garden.hobby_garden_server.hobby.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.*;
import hobby_garden.hobby_garden_server.hobby.dto.request.AddHobbyToUser;
import hobby_garden.hobby_garden_server.hobby.dto.request.DeleteHobbyRequest;
import hobby_garden.hobby_garden_server.hobby.dto.response.AllHobbiesResponse;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public BaseResponse<String> deleteHobbyFromUser(String token, DeleteHobbyRequest deleteHobbyRequest) {
        //* get user from user repository by user token, jwtService.extractUserName() returns username from token
        User user = getUserNameByToken(token);

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

    @Override
    public BaseResponse<String> addHobbyToUser(String token, AddHobbyToUser addHobbyToUser) {
        //* check if user exists, if not throw exception
        User user = getUserNameByToken(token);

        //* check if hobby exists, if not create it
        Hobby hobby = hobbyRepository.findByName(addHobbyToUser.getHobbyName()).orElse(null);

        if(hobby == null){
            try{
                hobby = new Hobby();
                hobby.setName(addHobbyToUser.getHobbyName());
                hobby = hobbyRepository.save(hobby);
            }
            catch (Exception e){
                throw new UnknownException(Strings.errorOccurWhileCreatingHobby + " " + e.getMessage());
            }
        }

        //* check if user already has hobby, if it has, throw exception
        if(user.getHobbies().contains(hobby)){
            throw new UserAlreadyHasThisHobby(Strings.userAlreadyHasThisHobby);
        }

        //* add hobby to user, then save user, then return response
        try{
            user.getHobbies().add(hobby);
            userRepository.save(user);

            return new BaseResponse<>(true, Strings.hobbyAdded, null);
        }
        catch (Exception e){
            throw new UnknownException(Strings.errorOccurWhileAddingHobby + " " + e.getMessage());
        }
    }

    @Override
    public BaseResponse<AllHobbiesResponse> getHobbies() {
        //* get all hobbies from db
        AllHobbiesResponse allHobbiesResponse = new AllHobbiesResponse();
        allHobbiesResponse.setHobbies(hobbyRepository.findAll());

        //* return response
        return new BaseResponse<>(true, Strings.hobbiesFound, allHobbiesResponse);
    }

    private User getUserNameByToken(String token) {
        String username = jwtService.extractUserNameWithBearer(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }
}
