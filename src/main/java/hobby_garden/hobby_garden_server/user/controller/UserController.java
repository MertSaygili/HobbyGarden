package hobby_garden.hobby_garden_server.user.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.user.dto.request.ForgotPasswordRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.request.UpdatePasswordRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;
import hobby_garden.hobby_garden_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public BaseResponse<SignInResponse> signIn(@RequestBody SignInRequest user) {
        return userService.signIn(user);
    }

    @PostMapping("/sign-up")
    public BaseResponse<Object> signUp(@RequestBody SignUpRequest user) {
        return userService.signUp(user);
    }

    @PutMapping("/updatePassword")
    public BaseResponse<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return userService.updatePassword(updatePasswordRequest);
    }

    @PatchMapping("/forgotPassword")
    public BaseResponse<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return userService.forgotPassword(forgotPasswordRequest);
    }

}
