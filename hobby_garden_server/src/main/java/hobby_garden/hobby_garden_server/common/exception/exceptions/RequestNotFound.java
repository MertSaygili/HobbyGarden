package hobby_garden.hobby_garden_server.common.exception.exceptions;

public class RequestNotFound extends RuntimeException{
    public RequestNotFound(String message) {
        super(message);
    }
}
