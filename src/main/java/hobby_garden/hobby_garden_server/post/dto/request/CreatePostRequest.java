package hobby_garden.hobby_garden_server.post.dto.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("class can be a record")
public class CreatePostRequest {
    private String userToken;
    private String title;
    private String content;
    private List<String> hobbyTagIds;

    @Nullable
    private List<MultipartFile> images;

    @Nullable
    private List<String> imageBase64s;

    @Nullable
    private List<String> videoBase64s;

    @Nullable
    private LocalDateTime updatedAt;
}
