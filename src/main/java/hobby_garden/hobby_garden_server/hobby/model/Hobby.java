package hobby_garden.hobby_garden_server.hobby.model;

import hobby_garden.hobby_garden_server.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.List;

@Data
@Node("Hobby")
@NoArgsConstructor
public class Hobby {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Property("name")
    private String name;
}
