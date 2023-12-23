package hobby_garden.hobby_garden_server.post.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.HobbyNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.PostNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserNotFoundException;
import hobby_garden.hobby_garden_server.common.utility.ImageConverter;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.UserPostsResponse;
import hobby_garden.hobby_garden_server.post.model.*;
import hobby_garden.hobby_garden_server.post.repository.DislikesRepository;
import hobby_garden.hobby_garden_server.post.repository.LikesRepository;
import hobby_garden.hobby_garden_server.post.repository.MediaRepository;
import hobby_garden.hobby_garden_server.post.repository.PostRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//TODO like dislike transaction

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;
    private final DislikesRepository dislikesRepository;
    private final LikesRepository likesRepository;
    private final JWTService jwtService;
    private final MediaRepository mediaRepository;
    private final ImageConverter imageConverter = new ImageConverter();

    @Override
    public BaseResponse<CreatePostResponse> createPost(CreatePostRequest request)  {
        //* check if user exist, first check if token is valid and extract username
        //* then check if user exists in db
        User user = getUserFromToken(request.getUserToken());

        //* check if hobby exists
        List<String> hobbyIds = request.getHobbyTagIds();
        List<Hobby> hobbies = new ArrayList<>(Collections.emptyList());
        for (String hobbyId : hobbyIds) {
            Hobby hobby = hobbyRepository.findById(hobbyId).orElse(null);
            if (hobby == null) {
                throw new HobbyNotFoundException(Strings.hobbyNotFound);
            }
            else {
                hobbies.add(hobby);
            }
        }

        //* create post
        Post post = new Post();
        post.setAuthor(user);
        post.setTags(hobbies);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setComments(new ArrayList<>(Collections.emptyList()));
        post.setLikes(new ArrayList<>(Collections.emptyList()));
        post.setDislikes(new ArrayList<>(Collections.emptyList()));

        //* check is there any media
        List<Media> medias = new ArrayList<>(Collections.emptyList());
        if(request.getImageBase64s() != null){
            for(String base64String : request.getImageBase64s()){
                Media media = new Media();

                // TODO: 2021-10-13 set media properties
                media.setFileName("image");
                media.setFileType("image");

                try{
                    BufferedImage image = imageConverter.base64StringToImage(base64String);
                    media.setBufferedImage(String.valueOf(image));

                    //* add media to post
                    medias.add(media);

                    mediaRepository.save(media);
                }
                catch (IOException e){
                    throw new UnknownException(Strings.errorOccurWhileCreatingPost + " " + e.getMessage());
                }
            }
        }

        //* add media to post
        post.setMedia(medias);

        //* save post to user and post repositories
        try {
            postRepository.save(post);

            user.getPosts().add(post);
            userRepository.save(user);

            //* return response
            return new BaseResponse<>(true, Strings.postCreated, null);
        }
        catch (Exception e){
            throw new UnknownException(Strings.errorOccurWhileCreatingPost + " " + e.getMessage());
        }
    }

    @Override
    public BaseResponse<String> likePost(LikeDislikeRequest request) {
        //* check if user exist, first check if token is valid and extract username
        User user = getUserFromToken(request.getUserToken());

        //* check if post exists
        Post post = getPostById(request.getPostId());

        //* check if user already liked this post
        List<Likes> likes = post.getLikes();
        for(Likes like : likes){
            if(like.getUser().getUserId().equals(user.getUserId())){
                //* if user already liked this post, then remove like
                post.getLikes().remove(like);
                postRepository.save(post);
                likesRepository.delete(like);
                return new BaseResponse<>(true, Strings.userRemoveLike, null);
            }
        }

        //* user didn't like this post, then add like
        Likes like = new Likes();
        like.setUser(user);
        like.setPost(post);

        post.getLikes().add(like);
        postRepository.save(post);

        //* if user already liked this post, then remove like
        return new BaseResponse<>(true, Strings.userLikedThePost, null);

    }

    @Override
    public BaseResponse<String> dislikePost(LikeDislikeRequest request) {
        //* get user
        User user = getUserFromToken(request.getUserToken());

        //* check if post exists
        Post post = getPostById(request.getPostId());

        //* check if user already disliked this post
        List<Dislikes> dislikes = post.getDislikes();
        for(Dislikes dislike : dislikes){
            if(dislike.getUser().getUserId().equals(user.getUserId())){
                //* if user already liked this post, then remove like
                post.getLikes().remove(dislike);
                postRepository.save(post);
                dislikesRepository.delete(dislike);
                return new BaseResponse<>(true, Strings.userRemoveDislike, null);
            }
        }

        //* user didn't like this post, then add dislike
        Dislikes dislike = new Dislikes();
        dislike.setUser(user);
        dislike.setPost(post);

        post.getDislikes().add(dislike);
        postRepository.save(post);

        return new BaseResponse<>(true, Strings.userDislikedThePost, null);
    }

    @Override
    public BaseResponse<String> commentPost(CommentsRequest request) {
        //* get user, check if user exists
        User user = getUserFromToken(request.getUserToken());

        //* check if post exists
        Post post = getPostById(request.getPostId());

        //* create comment
        Comments comment = new Comments();
        comment.setUser(user);
        comment.setText(request.getText());
        comment.setDate(LocalDateTime.now());

        //* add comment to post
        post.getComments().add(comment);

        //* save post
        postRepository.save(post);
        return new BaseResponse<>(true, Strings.commentedPost, null);
    }

    @Override
    public BaseResponse<List<UserPostsResponse>> getUserPosts(String token, String username) {
        //* get user, check if user exists
        User user = getUserFromToken(token.split(" ")[1]);

        //* get user posts
        List<Post> posts = user.getPosts();

        //* create response
        List<UserPostsResponse> userPostsResponses = new ArrayList<>(Collections.emptyList());

        //* add posts to response
        for(Post post : posts){

            UserPostsResponse userPostsResponse = new UserPostsResponse();

            userPostsResponse.setPostId(post.getPostId());
            userPostsResponse.setCreatorName(post.getAuthor().getUsername());
            userPostsResponse.setTitle(post.getTitle());
//            userPostsResponse.setContent(post.getContent());
//            userPostsResponse.setTags(post.getTags());
            userPostsResponse.setLikes(post.getLikes().size());
            userPostsResponse.setDislikes(post.getDislikes().size());
            userPostsResponse.setComments(post.getComments().size());
//            userPostsResponse.setImage(post.getMedia());
            userPostsResponse.setCreatedAt(post.getCreatedAt());

            userPostsResponses.add(userPostsResponse);
        }

        //* return response
        return new BaseResponse<>(true, Strings.userPostsFetched, userPostsResponses);

    }

    //* helper methods, extract user from token, check if user exists, etc.
    private User getUserFromToken(String token){
        String username = jwtService.extractUserName(token);
        if (username == null) {
            throw new UserNotFoundException(Strings.userNotFound);
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(Strings.userNotFound);
        }
        return user.get();
    }

    private Post getPostById(String postId){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new PostNotFoundException(Strings.postNotFound);
        }
        return post.get();
    }
}
