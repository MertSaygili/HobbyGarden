package hobby_garden.hobby_garden_server.post.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.GetCommentsResponse;
import hobby_garden.hobby_garden_server.post.dto.response.UserPostsResponse;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.token.Token;

import java.util.List;

public interface PostService {
    BaseResponse<CreatePostResponse> createPost(CreatePostRequest request);

    BaseResponse<String> likePost(LikeDislikeRequest request);

    BaseResponse<String> dislikePost(LikeDislikeRequest request);

    BaseResponse<String> commentPost(CommentsRequest request);

    BaseResponse<List<UserPostsResponse>> getUserPosts(String token, String username);

    BaseResponse<List<GetCommentsResponse>> getCommentsOfPost(String postId);
}
