package hobby_garden.hobby_garden_server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@Node("User")
public class User {
    @GeneratedValue(UUIDStringGenerator.class)
    @Id
    private String userId;

    @Property("username")
    private String username;

    @Property("first_name_last_name")
    private String firstNameLastName;

    @Property("password")
    private String password;

    @Property("email")
    private String email;

    @Property("hobbies")
    private List<String> hobbies;

    @Property("created_at")
    private LocalDateTime createdAt;
}
