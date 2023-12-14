package hobby_garden.hobby_garden_server.user.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.enums.LogLevel;
import hobby_garden.hobby_garden_server.log.model.LogModel;
import hobby_garden.hobby_garden_server.log.model.LogRequestModel;
import hobby_garden.hobby_garden_server.log.model.LogResponseModel;
import hobby_garden.hobby_garden_server.log.repository.LogRepository;
import hobby_garden.hobby_garden_server.user.dto.CreateUser;
import hobby_garden.hobby_garden_server.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    LogRepository logRepository;

    @GetMapping("/{id}")
    public BaseResponse<String> getUser(@RequestParam(value = "id", defaultValue = "0") String id) {
        return new BaseResponse<String>(true, "Asd", "Asd");
    }

    @PostMapping("/create")
    public BaseResponse<String> createUser(@RequestBody CreateUser user) {
        return new BaseResponse<String>(false, "User already exists", "selam");
    }

    @PostMapping("/log")
    public BaseResponse<String> logUser() {
        return new BaseResponse<String>(false, "User already exists", "selam");
    }
}
