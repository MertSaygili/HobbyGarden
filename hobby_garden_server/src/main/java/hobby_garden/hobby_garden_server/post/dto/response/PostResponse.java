package hobby_garden.hobby_garden_server.post.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String postId;
    private String title;
    private String content;
    private String image;
    private String creatorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likes;
    private int dislikes;
    private int comments;
}
