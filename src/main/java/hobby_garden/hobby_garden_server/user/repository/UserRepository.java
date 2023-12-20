package hobby_garden.hobby_garden_server.user.repository;

import hobby_garden.hobby_garden_server.user.entity.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (u:User) WHERE u.username = $username RETURN u")
    Optional<User> findByUsername(@Param("username") String username);
}
