package hobby_garden.hobby_garden_server.post.model;

import hobby_garden.hobby_garden_server.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RelationshipProperties
public class Comments {
    @RelationshipId
    private String id;

    @Property("text")
    private String text;

    @Property("date")
    private LocalDateTime date;

    @Property("user")
    @TargetNode
    private User user;
}
