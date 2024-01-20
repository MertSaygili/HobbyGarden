package hobby_garden.hobby_garden_server.chat.model;


import lombok.Data;

@Data
public class Message {
    private String message;
    private String receiverName;
}
