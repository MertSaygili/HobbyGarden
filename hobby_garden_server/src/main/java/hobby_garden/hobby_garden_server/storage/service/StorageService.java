package hobby_garden.hobby_garden_server.storage.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    BaseResponse<?> uploadImageToPost(String token, String postId, MultipartFile imageBase64);
    ResponseEntity<?> getImage(String token, long fileId);
}
