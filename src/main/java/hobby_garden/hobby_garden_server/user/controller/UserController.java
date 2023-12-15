package hobby_garden.hobby_garden_server.user.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.log.repository.LogRepository;
import hobby_garden.hobby_garden_server.user.dto.request.CreateUser;
import hobby_garden.hobby_garden_server.user.dto.response.UserResponse;
import hobby_garden.hobby_garden_server.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public BaseResponse<String> getUser(@RequestParam(value = "id", defaultValue = "0") String id) {
        return new BaseResponse<String>(true, "Asd", "Asd");
    }

    @PostMapping("/create")
    public BaseResponse<UserResponse> createUser(@RequestBody CreateUser user) {
        return userService.createUser(user);
    }

    @PostMapping("/log")
    public BaseResponse<String> logUser() {
        return new BaseResponse<String>(false, "User already exists", "selam");
    }
}
