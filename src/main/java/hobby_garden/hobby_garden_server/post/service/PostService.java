package hobby_garden.hobby_garden_server.post.service;

import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;

public interface PostService {
    BaseResponse<CreatePostResponse> createPost(CreatePostRequest request);
}
