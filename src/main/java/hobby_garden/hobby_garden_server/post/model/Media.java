package hobby_garden.hobby_garden_server.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Media")
public class Media {

    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Id
    private String id;

    @Property("file_name")
    private String fileName;

    @Property("file_type")
    private String fileType;

    @Property("image")
    private String BufferedImage;
}
