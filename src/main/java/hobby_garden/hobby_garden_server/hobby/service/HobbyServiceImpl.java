package hobby_garden.hobby_garden_server.hobby.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.exception.exceptions.BadHobbyNameFormat;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService{

    private final HobbyRepository hobbyRepository;


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
}
