package hobby_garden.hobby_garden_server.post.model;

import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.user.model.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Post")
public class Post {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Property("post_id")
    String postId;

    @Property("author")
    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.INCOMING)
    User author;

    @Property("title")
    String title;

    @Property("content")
    String content;

    @Nullable
    @Property("tags")
    List<Hobby> tags;

    @Nullable
    @Property("likes")
    @Relationship(type = "HAS_LIKE", direction = Relationship.Direction.OUTGOING)
    List<Likes> likes;

    @Nullable
    @Property("dislikes")
    @Relationship(type = "HAS_DISLIKE", direction = Relationship.Direction.OUTGOING)
    List<Dislikes> dislikes;

    @Nullable
    @Property("comments")
    @Relationship(type = "HAS_COMMENT", direction = Relationship.Direction.OUTGOING)
    List<Comments> comments;

    @Nullable
    @Property("media")
    List<Media> media;

    @Nullable
    @Property("created_at")
    LocalDateTime createdAt;

    @Nullable
    @Property("updated_at")
    LocalDateTime updatedAt;
}
