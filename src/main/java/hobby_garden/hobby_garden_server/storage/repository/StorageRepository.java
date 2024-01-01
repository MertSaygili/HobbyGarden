package hobby_garden.hobby_garden_server.storage.repository;

import hobby_garden.hobby_garden_server.storage.model.Image;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorageRepository extends Neo4jRepository<Image, Long> {

    @Query("MATCH (i:Image {file_name: $fileName}) RETURN i")
    Optional<Image> findImageByName(@Param("fileName") String fileName);
}
