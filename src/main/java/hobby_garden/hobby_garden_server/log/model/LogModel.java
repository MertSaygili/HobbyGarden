package hobby_garden.hobby_garden_server.log.model;

import hobby_garden.hobby_garden_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.neo4j.core.schema.Id;

@Data
@AllArgsConstructor
@ToString
@Document(collection = "logs")
public class LogModel {
    // log id
    @MongoId
    private String id;
    // log request
    private LogRequestModel<?> logRequest;
    // log response
    private LogResponseModel<?> logResponse;
    // user who made the request
    private User user;
}
