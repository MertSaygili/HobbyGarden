package hobby_garden.hobby_garden_server.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsRequest {
    private String userToken;
    private String postId;
    private String text;
}
