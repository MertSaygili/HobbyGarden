package hobby_garden.hobby_garden_server.hobby.service;


import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.hobby.dto.request.AddHobbyToUser;
import hobby_garden.hobby_garden_server.hobby.dto.request.DeleteHobbyRequest;
import hobby_garden.hobby_garden_server.hobby.dto.response.AllHobbiesResponse;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;

public interface HobbyService {
    Hobby createHobbyToUser(String hobbyName);

    BaseResponse<String> deleteHobbyFromUser(String token, DeleteHobbyRequest deleteHobbyRequest);

    BaseResponse<String> addHobbyToUser(String token, AddHobbyToUser addHobbyToUser);

    BaseResponse<AllHobbiesResponse> getHobbies();
}
