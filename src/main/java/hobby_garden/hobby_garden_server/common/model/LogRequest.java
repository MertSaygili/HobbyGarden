package hobby_garden.hobby_garden_server.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import hobby_garden.hobby_garden_server.common.enums.LogLevel;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class LogRequest<T> {
    // http method
    private HttpMethod httpMethod;
    // timestamp, when the request was made
    private Timestamp timestamp;
    // url, where the request was made
    private String url;
    // request size, how big the request is
    @Nullable
    private T data;
}
