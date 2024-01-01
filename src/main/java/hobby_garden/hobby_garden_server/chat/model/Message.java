package hobby_garden.hobby_garden_server.chat.model;


import hobby_garden.hobby_garden_server.user.model.User;
import lombok.Data;

@Data
public class Message {
    private User sender;
    private User receiver;
    private String message;
}
