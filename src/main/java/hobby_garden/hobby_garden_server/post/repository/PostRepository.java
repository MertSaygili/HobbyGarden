package hobby_garden.hobby_garden_server.post.repository;


import hobby_garden.hobby_garden_server.post.model.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PostRepository extends Neo4jRepository<Post, String> {
}