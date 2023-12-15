package hobby_garden.hobby_garden_server.common.exception;

import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
