package hobby_garden.hobby_garden_server.post.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.GetCommentsResponse;
import hobby_garden.hobby_garden_server.post.dto.response.PostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.UserPostsResponse;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.token.Token;

import java.util.List;

public interface PostService {
    BaseResponse<CreatePostResponse> createPost(String token, CreatePostRequest request);

    BaseResponse<String> likePost(String token, LikeDislikeRequest request);

    BaseResponse<String> dislikePost(String token, LikeDislikeRequest request);

    BaseResponse<String> commentPost(String token, CommentsRequest request);

    BaseResponse<List<UserPostsResponse>> getUserPosts(String token, String username);

    BaseResponse<List<GetCommentsResponse>> getCommentsOfPost(String token, String postId);

    BaseResponse<PostResponse> getPostByPostId(String token, String postId);
}
