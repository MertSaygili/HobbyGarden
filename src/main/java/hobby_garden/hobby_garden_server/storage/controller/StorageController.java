package hobby_garden.hobby_garden_server.storage.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/getImage/{fileName}")
    public BaseResponse<?> get(@PathVariable("fileName") String fileName) {
        return storageService.getImage(fileName);
    }

    @PostMapping("/uploadImage")
    public BaseResponse<?> upload(@RequestParam("postId") String postId,  @RequestParam("image") MultipartFile image) {
        return storageService.uploadImageToPost(postId, image);
    }
}
