package hobby_garden.hobby_garden_server.common.exception;

/*
 * This class is used to handle all the exceptions that are thrown from the service layer,
 * and then we will send the response to the client. The second throw!
 * If you want to add new exception, you should add it here
 */

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.exception.exceptions.*;


public class FilterExceptions extends RuntimeException{
     public FilterExceptions(String message) {
        super(message);
        switch (message){
            case Strings.userNotFound -> throw new UserNotFoundException(message);
            case Strings.invalidToken -> throw new InvalidTokenException(message);
            case Strings.userAlreadyExist -> throw new UserAlreadyExist(message);
            case Strings.userAlreadyHasThisHobby -> throw new UserAlreadyHasThisHobby(message);
            case Strings.postNotFound -> throw new PostNotFoundException(message);
            case Strings.hobbyNotFound -> throw new HobbyNotFoundException(message);
            case Strings.errorOccurWhileCreatingHobby -> throw new ErrorWhileCreatingHobby(message);
            case Strings.hobbyNameIsEmpty -> throw new BadHobbyNameFormat(message);
            case Strings.requestNotFound -> throw new RequestNotFound(message);
            default -> throw new UnknownException(message);
        }

    }
}