package hobby_garden.hobby_garden_server.log.model;

import hobby_garden.hobby_garden_server.common.enums.LogLevel;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogResponseModel<T> {
    // log level
    private LogLevel logLevel;
    // http status codes
    private int httpStatusCode;
    // response time, how long it took to process the request
    private String responseTime;
    // response size, how big the response is
    private long responseSize;
    // response data
    @Nullable
    private T data;
}
