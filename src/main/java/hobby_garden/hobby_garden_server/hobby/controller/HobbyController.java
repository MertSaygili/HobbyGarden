package hobby_garden.hobby_garden_server.hobby.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.hobby.dto.request.DeleteHobbyRequest;
import hobby_garden.hobby_garden_server.hobby.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/hobby")
@SuppressWarnings("unused")
public class HobbyController {
    private final HobbyService hobbyService;
    @PostMapping("/delete")
    public BaseResponse<String> deleteHobbyFromUser(@RequestBody DeleteHobbyRequest deleteHobbyRequest) {
        System.out.println("deleteHobbyRequest: " + deleteHobbyRequest);
        return  hobbyService.deleteHobbyFromUser(deleteHobbyRequest);
    }
}
