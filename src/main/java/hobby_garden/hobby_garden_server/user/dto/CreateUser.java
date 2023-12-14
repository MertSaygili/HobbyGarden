package hobby_garden.hobby_garden_server.user.dto;

import lombok.Data;

@Data
public class CreateUser {
    public String email;
    public String password;
    public String name;
}
