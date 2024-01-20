package hobby_garden.hobby_garden_server.hobby_swap.dto;

import lombok.Data;

@Data
public class RequestHobbyRequest {
    private String requestedHobbyId;
    private String requestedUserId;
}
