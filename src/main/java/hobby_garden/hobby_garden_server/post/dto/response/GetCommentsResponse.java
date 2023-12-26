package hobby_garden.hobby_garden_server.post.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCommentsResponse {
    private String commentId;
    private String commentText;
    private LocalDateTime commentDate;
    private GetCommentsUser user;

    @Data
    public static class GetCommentsUser{
        private String userId;
        private String username;
        private String firstNameLastName;
        private String email;
        private LocalDateTime createdAt;
    }
}

