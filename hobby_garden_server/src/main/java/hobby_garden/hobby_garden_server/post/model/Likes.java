package hobby_garden.hobby_garden_server.post.model;

import hobby_garden.hobby_garden_server.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RelationshipProperties
public class Likes {
    @RelationshipId
    private String id;

    @Property("date")
    private LocalDateTime date;

    @Property("user")
    @TargetNode
    private User user;
}
