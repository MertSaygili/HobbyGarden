package hobby_garden.hobby_garden_server.post.repository;

import hobby_garden.hobby_garden_server.post.model.Dislikes;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DislikesRepository extends Neo4jRepository<Dislikes, String> {
}
