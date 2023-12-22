package hobby_garden.hobby_garden_server.post.repository;

import hobby_garden.hobby_garden_server.post.model.Likes;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface LikesRepository extends Neo4jRepository<Likes, String> {
}
