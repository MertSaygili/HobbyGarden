package hobby_garden.hobby_garden_server.hobby.service;


import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.hobby.dto.request.AddHobbyToUser;
import hobby_garden.hobby_garden_server.hobby.dto.request.DeleteHobbyRequest;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;

public interface HobbyService {
    Hobby createHobbyToUser(String hobbyName);

    BaseResponse<String> deleteHobbyFromUser(DeleteHobbyRequest deleteHobbyRequest);

    BaseResponse<String> addHobbyToUser(AddHobbyToUser addHobbyToUser);
}
