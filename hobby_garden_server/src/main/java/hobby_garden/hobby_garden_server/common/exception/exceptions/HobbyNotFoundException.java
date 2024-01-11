package hobby_garden.hobby_garden_server.common.exception.exceptions;

public class HobbyNotFoundException extends RuntimeException{
    public HobbyNotFoundException(String message) {
        super(message);
    }
}
