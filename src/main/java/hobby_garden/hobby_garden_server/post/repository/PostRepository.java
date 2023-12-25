package hobby_garden.hobby_garden_server.post.repository;


import hobby_garden.hobby_garden_server.post.model.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends Neo4jRepository<Post, String> {

    //* create like relation between user and post
    @Query("Match (p1:User {user_id: $userId}), (p2:Post {post_id: $postId}) Create (p1)-[:LIKES]->(p2)")
    void likePost(@Param("userId") String userId, @Param("postId") String postId);

    //* delete like relation between user and post
    @Query("Match (p1:User {user_id: '$userId' })-[p1:LIKES]->(p2:Post {post_id: $postId}) Delete r")
    void unlikePost(@Param("userId") String userId, @Param("postId") String postId);

    //* create comment relation between user and post
    @Query("Match (p1:User {user_id: $userId}), (p2:Post {post_id: $postId}) Create (p1)-[:COMMENTED]->(p2)")
    void commentPost(@Param("userId") String userId, @Param("postId") String postId);

    //* dislike post
//    @Query("Match (p1:User {user_id: $userId}), (p2:Post {post_id: $postId}) Create (p1)-[:DISLIKES]->(p2)")
//    void dislikePost(@Param("userId") String userId, @Param("postId") String postId);

    //* delete dislike relation between user and post
    @Query("Match (p1:User {user_id: $userId})-[r:DISLIKES]->(p2:Post {post_id: $postId}) Delete r")
    void undislikePost(@Param("userId") String userId, @Param("postId") String postId);
}