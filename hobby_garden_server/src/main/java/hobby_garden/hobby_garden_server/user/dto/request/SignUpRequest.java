package hobby_garden.hobby_garden_server.user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String firstNameLastName;
    private String password;
    private String email;
    private List<String> hobbies;
}
