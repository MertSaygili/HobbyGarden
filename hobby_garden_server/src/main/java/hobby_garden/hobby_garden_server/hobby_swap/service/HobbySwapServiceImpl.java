package hobby_garden.hobby_garden_server.hobby_swap.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.FilterExceptions;
import hobby_garden.hobby_garden_server.common.exception.exceptions.HobbyNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.RequestNotFound;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import hobby_garden.hobby_garden_server.hobby_swap.dto.AnswerTalentRequest;
import hobby_garden.hobby_garden_server.hobby_swap.dto.RequestHobbyRequest;
import hobby_garden.hobby_garden_server.hobby_swap.model.HobbySwapModel;
import hobby_garden.hobby_garden_server.hobby_swap.repository.HobbySwapRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HobbySwapServiceImpl implements HobbySwapService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final HobbySwapRepository swapRepository;
    private final HobbyRepository hobbyRepository;

    @Override
    public BaseResponse<String> requestTalent(String token, RequestHobbyRequest body) {
        try{
            User mainUser = getUserByToken(token);
            User requestedUser = getUser(body.getRequestedUserId());

            Hobby hobby =  hobbyRepository.findById(body.getRequestedHobbyId()).orElseThrow(() -> new HobbyNotFoundException(Strings.hobbyNotFound));

            HobbySwapModel swapModel = HobbySwapModel.builder().
                    mainUser(mainUser).
                    requestedUser(requestedUser).
                    requestedDate(LocalDateTime.now()).
                    requestedHobby(hobby).
                    isApproved(false).build();

            swapRepository.save(swapModel);

            return new BaseResponse<>(true, Strings.talentRequested, null);

        }
        catch (Exception e){
            throw new FilterExceptions(e.getMessage());
        }
    }

    @Override
    public BaseResponse<String> answerTalentRequest(String token, AnswerTalentRequest request) {
        try{
            getUserByToken(token);
            HobbySwapModel swapModel = swapRepository.findById(request.getRequestId()).orElseThrow(() ->  new RequestNotFound(Strings.requestNotFound));

            swapModel.setApproved(request.isAnswer());
            swapRepository.save(swapModel);


            if(swapModel.isApproved()){
                return new BaseResponse<>(true, Strings.talentRequestAccepted, null);
            }

            return new BaseResponse<>(true, Strings.talentRequestRejected, null);
        }
        catch (Exception e){
            throw new FilterExceptions(e.getMessage());
        }
    }


    //* helper methods, extract user from token, check if user exists, etc.
    private User getUserByToken(String token) {
        String username = jwtService.extractUserNameWithBearer(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }

    private User getUser(String userId){
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }
}
