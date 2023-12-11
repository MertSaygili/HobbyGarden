package hobby_garden.hobby_garden_server.common.model;

import hobby_garden.hobby_garden_server.user.entity.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@ToString
@Document(collection = "logs")
public class Log {
    // log request
    private LogRequest<?> logRequest;
    // log response
    private LogResponse<?> logResponse;
    // user who made the request
    private User user;
}
