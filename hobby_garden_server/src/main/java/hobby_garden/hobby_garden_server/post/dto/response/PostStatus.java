package hobby_garden.hobby_garden_server.post.dto.response;

import lombok.Data;

@Data
public class PostStatus {
    private long likeCount;
    private long dislikeCount;
    private long commentCount;
}
