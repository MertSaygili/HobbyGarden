package hobby_garden.hobby_garden_server.chat.controller;

import hobby_garden.hobby_garden_server.chat.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {
    private SimpMessagingTemplate messageTemplate;

    @MessageMapping("/message")
    public void sendMessage(@Payload Message message) {
        messageTemplate.convertAndSend("/topic", message);
    }
}
