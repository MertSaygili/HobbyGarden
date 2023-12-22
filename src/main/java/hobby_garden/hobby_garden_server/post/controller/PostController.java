package hobby_garden.hobby_garden_server.post.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
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

    @GetMapping("/get/{postId}")
    BaseResponse<String> getPost(@PathVariable Long postId) {
        return null;
    }

    @PostMapping("/like-unlike")
    BaseResponse<String> likeUnlikePost(@RequestBody LikeDislikeRequest request) {
        return postService.likePost(request);
    }

    @PostMapping("/dislike-unlike")
    BaseResponse<String> dislikeUndislikePost(@RequestBody LikeDislikeRequest request) {
        return postService.dislikePost(request);
    }

    @PostMapping("/comment/{postId}")
    BaseResponse<String> commentPost(@RequestBody CommentsRequest request) {
        return postService.commentPost(request);
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
