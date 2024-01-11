package hobby_garden.hobby_garden_server.common.exception.exceptions;

public class UserAlreadyHasThisHobby extends RuntimeException {
    public UserAlreadyHasThisHobby(String message) {
        super(message);
    }
}
