package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;

public interface UserService {
     BaseResponse<SignInResponse> signIn(SignInRequest signInRequest);
     BaseResponse<Object> signUp(SignUpRequest signUpRequest);
}
