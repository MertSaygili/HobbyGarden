package hobby_garden.hobby_garden_server.post.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    BaseResponse<CreatePostResponse> createPost(@RequestBody CreatePostRequest requestBody) {
        return postService.createPost(requestBody);
    }

    @GetMapping("get/{postId}")
    BaseResponse<String> getPost(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/like/{postId}")
    BaseResponse<String> likePost(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/dislike/{postId}")
    BaseResponse<String> unlikePost(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/comment/{postId}")
    BaseResponse<String> commentPost(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/delete/{postId}")
    BaseResponse<String> deletePost(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/update/{postId}")
    BaseResponse<String> updatePost(@PathVariable Long postId) {
        return null;
    }

    @GetMapping("/get-all")
    BaseResponse<String> getAllPosts() {
        return null;
    }
}
