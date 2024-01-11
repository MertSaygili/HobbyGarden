package hobby_garden.hobby_garden_server.storage.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Builder
@Node("Image")
public class Image {
    @Id
    @GeneratedValue()
    private long id;

    @Property("file_name")
    private String fileName;

    @Property("file_type")
    private String fileType;

    @Property("data")
    private byte[] data;
}
