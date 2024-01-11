package hobby_garden.hobby_garden_server.user.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.user.dto.request.ForgotPasswordRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.request.UpdatePasswordRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;
import hobby_garden.hobby_garden_server.user.model.User;

public interface UserService {
     BaseResponse<SignInResponse> signIn(SignInRequest signInRequest);
     BaseResponse<Object> signUp(SignUpRequest signUpRequest);
     BaseResponse<String> updatePassword(UpdatePasswordRequest updatePasswordRequest);
     BaseResponse<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
