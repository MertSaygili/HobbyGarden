package hobby_garden.hobby_garden_server.common.exception;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandling {

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<BaseResponse<Object>> handleUnknownException(UnknownException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));

    }

    @ExceptionHandler(BadHobbyNameFormat.class)
    public ResponseEntity<BaseResponse<Object>> handleBadHobbyNameFormat(BadHobbyNameFormat e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(HobbyNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleHobbyNotFoundException(HobbyNotFoundException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(UserAlreadyHasThisHobby.class)
    public ResponseEntity<BaseResponse<Object>> handleUserAlreadyHasThisHobby(UserAlreadyHasThisHobby e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(ErrorWhileCreatingHobby.class)
    public ResponseEntity<BaseResponse<Object>> handleErrorWhileCreatingHobby(ErrorWhileCreatingHobby e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }


    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<BaseResponse<Object>> handleUserAlreadyExist(UserAlreadyExist e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(false, e.getMessage(), null));
    }
}
