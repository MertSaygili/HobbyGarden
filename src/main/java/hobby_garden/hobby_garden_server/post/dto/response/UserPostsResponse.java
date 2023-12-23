package hobby_garden.hobby_garden_server.post.dto.response;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPostsResponse {
    private String postId;
    private String title;
    private String description;
    @Nullable
    private String image;
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime updatedAt;
    private String creatorName;
    private int likes;
    private int dislikes;
    private int comments;
}
