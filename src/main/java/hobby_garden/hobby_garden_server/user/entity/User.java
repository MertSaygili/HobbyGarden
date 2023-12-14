package hobby_garden.hobby_garden_server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@AllArgsConstructor
@ToString
@Node("User")
public class User {
    @GeneratedValue(UUIDStringGenerator.class)
    @Id
    private String userId;

    private String username;
    private String password;
    private String email;
}
