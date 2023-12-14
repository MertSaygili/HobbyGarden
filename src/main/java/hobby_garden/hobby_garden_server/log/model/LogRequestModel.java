package hobby_garden.hobby_garden_server.log.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class LogRequestModel<T> {
    // http method
    private String httpMethod;
    // timestamp, when the request was made
    private String timestamp;
    // url, where the request was made
    private String url;
    // request size, how big the request is
    @Nullable
    private T data;
}
