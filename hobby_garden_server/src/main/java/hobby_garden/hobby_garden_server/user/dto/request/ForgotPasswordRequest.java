package hobby_garden.hobby_garden_server.user.dto.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String username;
    private String email;
    private String newPassword;
}
