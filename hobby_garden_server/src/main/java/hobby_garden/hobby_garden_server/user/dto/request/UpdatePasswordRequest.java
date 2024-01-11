package hobby_garden.hobby_garden_server.user.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String userToken;
    private String oldPassword;
    private String newPassword;
}
