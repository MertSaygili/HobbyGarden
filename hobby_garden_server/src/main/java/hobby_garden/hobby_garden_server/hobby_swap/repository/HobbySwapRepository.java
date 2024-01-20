package hobby_garden.hobby_garden_server.hobby_swap.repository;

import hobby_garden.hobby_garden_server.hobby_swap.model.HobbySwapModel;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface HobbySwapRepository extends Neo4jRepository<HobbySwapModel, String> {
}
