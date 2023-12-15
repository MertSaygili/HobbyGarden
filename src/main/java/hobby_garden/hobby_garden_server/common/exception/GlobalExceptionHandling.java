package hobby_garden.hobby_garden_server.common.exception;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserAlreadyExist;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// trying something new
@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return "User not found";
    }

    @ExceptionHandler(UnknownException.class)
    public String handleUnknownException(UnknownException e) {
        return "Unknown exception";
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<BaseResponse<Object>> handleUserAlreadyExist(UserAlreadyExist e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }
}
