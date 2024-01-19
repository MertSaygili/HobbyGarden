package hobby_garden.hobby_garden_server.storage.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/getImage/{fileName}")
    public ResponseEntity<?> get(@RequestHeader(value = "Authorization") String token, @PathVariable("fileName") long fileId) {
        return storageService.getImage(token, fileId);
    }

    @PostMapping("/uploadImage")
    public BaseResponse<?> upload(@RequestHeader(value = "Authorization") String token, @RequestParam("postId") String postId,  @RequestParam("image") MultipartFile image) {
        return storageService.uploadImageToPost(token, postId, image);
    }
}
