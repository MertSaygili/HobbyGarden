package hobby_garden.hobby_garden_server.hobby.dto.request;

import lombok.Data;

@Data
public class DeleteHobbyRequest {
    private String hobbyId;
    private String userToken;
}
