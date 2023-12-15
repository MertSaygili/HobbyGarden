package hobby_garden.hobby_garden_server.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String name;
    private String username;
    private List<String> hobbies;
    private String createdAt;
}
