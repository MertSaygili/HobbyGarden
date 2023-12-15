package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.user.dto.request.CreateUser;
import hobby_garden.hobby_garden_server.user.dto.response.UserResponse;

public interface UserService {
     BaseResponse<UserResponse> createUser(CreateUser createUser);
}
