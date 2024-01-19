package hobby_garden.hobby_garden_server.post.controller;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.GetCommentsResponse;
import hobby_garden.hobby_garden_server.post.dto.response.PostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.UserPostsResponse;
import hobby_garden.hobby_garden_server.post.model.Post;
import hobby_garden.hobby_garden_server.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class PostController {
    private final PostService postService;

    @GetMapping("/get/{username}")
    BaseResponse<List<UserPostsResponse>> getUserPosts (@RequestHeader(value = "Authorization") String token, @PathVariable("username") String username) {
        return postService.getUserPosts(token, username);
    }

    @PostMapping("/create")
    BaseResponse<CreatePostResponse> createPost(@RequestHeader(value = "Authorization") String token, @RequestBody CreatePostRequest requestBody) {
        return postService.createPost(token, requestBody);
    }

    @PostMapping("/like-unlike")
    BaseResponse<String> likeUnlikePost(@RequestHeader(value = "Authorization") String token, @RequestBody LikeDislikeRequest request) {
        return postService.likePost(token, request);
    }

    @PostMapping("/dislike-unlike")
    BaseResponse<String> dislikeUndislikePost(@RequestHeader(value = "Authorization") String token, @RequestBody LikeDislikeRequest request) {
        return postService.dislikePost(token, request);
    }

    @PostMapping("/comment")
    BaseResponse<String> commentPost(@RequestHeader(value = "Authorization") String token, @RequestBody CommentsRequest request) {
        return postService.commentPost(token, request);
    }

    @GetMapping("/getComments/{postId}")
    BaseResponse<List<GetCommentsResponse>> getComments(@RequestHeader(value = "Authorization") String token, @PathVariable("postId") String postId) {
        return postService.getCommentsOfPost(token, postId);
    }

    @GetMapping("/get")
    BaseResponse<PostResponse> getPostByPostId(@RequestHeader(value = "Authorization") String token, @RequestParam("postId") String postId) {
        return postService.getPostByPostId(token, postId);
    }
}
