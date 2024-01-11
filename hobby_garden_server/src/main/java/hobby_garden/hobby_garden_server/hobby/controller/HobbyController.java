package hobby_garden.hobby_garden_server.hobby.controller;


import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.hobby.dto.request.AddHobbyToUser;
import hobby_garden.hobby_garden_server.hobby.dto.request.DeleteHobbyRequest;
import hobby_garden.hobby_garden_server.hobby.dto.response.AllHobbiesResponse;
import hobby_garden.hobby_garden_server.hobby.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/hobby")
@SuppressWarnings("unused")
public class HobbyController {
    private final HobbyService hobbyService;

    @GetMapping("/get")
    public BaseResponse<AllHobbiesResponse> getHobbies() {
        return hobbyService.getHobbies();
    }

    @DeleteMapping("/delete")
    public BaseResponse<String> deleteHobbyFromUser(@RequestBody DeleteHobbyRequest deleteHobbyRequest) {
        return hobbyService.deleteHobbyFromUser(deleteHobbyRequest);
    }

    @PostMapping("/add")
    public BaseResponse<String> addHobbyToUser(@RequestBody AddHobbyToUser addHobbyToUser) {
        return hobbyService.addHobbyToUser(addHobbyToUser);
    }
}
