package hobby_garden.hobby_garden_server.hobby.repository;


import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface HobbyRepository extends Neo4jRepository<Hobby, String> {
    @Query("MATCH (h:Hobby) WHERE h.name = $name RETURN h")
    Optional<Hobby> findByName(String name);
}
