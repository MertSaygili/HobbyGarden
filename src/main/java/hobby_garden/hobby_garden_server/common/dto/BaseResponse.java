package hobby_garden.hobby_garden_server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class BaseResponse<T> {

    private final boolean success;
    private final String message;
    @Nullable
    private final T data;
}
