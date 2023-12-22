package hobby_garden.hobby_garden_server.post.repository;

import hobby_garden.hobby_garden_server.post.model.Media;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MediaRepository extends Neo4jRepository<Media, String> {
}
