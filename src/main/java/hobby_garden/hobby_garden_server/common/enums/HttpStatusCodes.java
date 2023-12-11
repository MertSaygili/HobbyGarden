package hobby_garden.hobby_garden_server.common.enums;

import lombok.Getter;

//* Http status codes enum, used to determine the http status code of the log response


@Getter
public enum HttpStatusCodes {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503);

    private final int value;

    HttpStatusCodes(int value) {
        this.value = value;
    }

}
