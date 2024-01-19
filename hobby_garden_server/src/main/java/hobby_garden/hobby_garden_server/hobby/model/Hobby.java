package hobby_garden.hobby_garden_server.hobby.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

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
