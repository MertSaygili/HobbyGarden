package hobby_garden.hobby_garden_server.post.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.GetCommentsResponse;
import hobby_garden.hobby_garden_server.post.dto.response.UserPostsResponse;
import hobby_garden.hobby_garden_server.post.service.PostService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.Token;
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
    BaseResponse<CreatePostResponse> createPost(@RequestBody CreatePostRequest requestBody) {
        return postService.createPost(requestBody);
    }

    @PostMapping("/like-unlike")
    BaseResponse<String> likeUnlikePost(@RequestBody LikeDislikeRequest request) {
        return postService.likePost(request);
    }

    @PostMapping("/dislike-unlike")
    BaseResponse<String> dislikeUndislikePost(@RequestBody LikeDislikeRequest request) {
        return postService.dislikePost(request);
    }

    @PostMapping("/comment")
    BaseResponse<String> commentPost(@RequestBody CommentsRequest request) {
        System.out.println(request);
        return postService.commentPost(request);
    }

    @GetMapping("/getComments/{postId}")
    BaseResponse<List<GetCommentsResponse>> getComments(@PathVariable("postId") String postId) {
        System.out.println(postId);
        return postService.getCommentsOfPost(postId);
    }

}
