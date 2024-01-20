package hobby_garden.hobby_garden_server.hobby_swap.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.hobby_swap.dto.AnswerTalentRequest;
import hobby_garden.hobby_garden_server.hobby_swap.dto.RequestHobbyRequest;
import hobby_garden.hobby_garden_server.hobby_swap.service.HobbySwapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/swap")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class HobbySwapController {

    private final HobbySwapService talentSwapService;


    @PostMapping("/request")
    public BaseResponse<String> requestTalent(@RequestHeader("Authorization") String token, @RequestBody RequestHobbyRequest request){
        return talentSwapService.requestTalent(token, request);
    }

    @PostMapping("/answerRequest")
    public BaseResponse<String> answerTalentRequest(@RequestHeader("Authorization") String token, @RequestBody AnswerTalentRequest request){
        return talentSwapService.answerTalentRequest(token, request);
    }

}
