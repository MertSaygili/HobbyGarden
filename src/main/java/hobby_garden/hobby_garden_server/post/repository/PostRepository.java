package hobby_garden.hobby_garden_server.post.repository;


import hobby_garden.hobby_garden_server.post.model.Post;
import org.springframework.cglib.core.Local;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PostRepository extends Neo4jRepository<Post, String> {

    //* create like relation between user and post
    @Query("Match (p1:User {user_id: $userId}), (p2:Post {post_id: $postId}) Create (p1)-[:LIKES {date:$date}]->(p2)")
    void likePost(@Param("userId") String userId, @Param("postId") String postId, @Param("date") LocalDateTime date);

    //* delete like relation between user and post
    @Query("Match (p1:User {user_id: $userId})-[r:LIKES]->(p2:Post {post_id: $postId}) Delete r")
    void unlikePost(@Param("userId") String userId, @Param("postId") String postId);

    //* create comment relation between user and post
    @Query("Match (p1:User {user_id: $userId}), (p2:Post {post_id: $postId}) Create (p1)-[:COMMENTED {text: $text, date:$date}]->(p2)")
    void commentPost(@Param("userId") String userId, @Param("postId") String postId, @Param("text") String text, @Param("date") LocalDateTime date);

    //* check user liked post
    @Query("Match (p1:User {user_id: $userId})-[r:LIKES]->(p2:Post {post_id: $postId}) Return Count(r) > 0")
    boolean checkUserLikedPost(@Param("userId") String userId, @Param("postId") String postId);

    //* check user disliked post
    @Query("Match (p1:User {user_id: $userId})-[r:DISLIKES]->(p2:Post {post_id: $postId}) Return Count(r) > 0")
    boolean checkUserDislikedPost(@Param("userId") String userId, @Param("postId") String postId);

    //* dislike post
    @Query("Match (p1:User {user_id: $userId}), (p2:Post {post_id: $postId}) Create (p1)-[:DISLIKES {date:$date}]->(p2)")
    void dislikePost(@Param("userId") String userId, @Param("postId") String postId, @Param("date") LocalDateTime date);

    //* delete dislike relation between user and post
    @Query("Match (p1:User {user_id: $userId})-[r:DISLIKES]->(p2:Post {post_id: $postId}) Delete r")
    void undislikePost(@Param("userId") String userId, @Param("postId") String postId);
}