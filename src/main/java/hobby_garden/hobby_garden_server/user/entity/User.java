package hobby_garden.hobby_garden_server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@AllArgsConstructor
@ToString
@Node("User")
public class User {


}
