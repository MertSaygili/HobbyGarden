package hobby_garden.hobby_garden_server.user.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.user.dto.request.SignInRequest;
import hobby_garden.hobby_garden_server.user.dto.request.SignUpRequest;
import hobby_garden.hobby_garden_server.user.dto.response.SignInResponse;
import hobby_garden.hobby_garden_server.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

// swagger path = http://localhost:8080/swagger-ui/index.html

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
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
}
