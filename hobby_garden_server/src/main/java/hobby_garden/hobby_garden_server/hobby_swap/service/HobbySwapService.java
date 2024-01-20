package hobby_garden.hobby_garden_server.hobby_swap.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.hobby_swap.dto.AnswerTalentRequest;
import hobby_garden.hobby_garden_server.hobby_swap.dto.RequestHobbyRequest;

public interface HobbySwapService {

    BaseResponse<String> requestTalent(String token, RequestHobbyRequest body);

    BaseResponse<String> answerTalentRequest(String token, AnswerTalentRequest request);
}
