package hobby_garden.hobby_garden_server.post.model;

import hobby_garden.hobby_garden_server.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Dislikes")
public class Dislikes {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Property("user")
    private User user;

    @Property("post")
    private Post post;
}
