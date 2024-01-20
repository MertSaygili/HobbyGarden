package hobby_garden.hobby_garden_server.post.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.FilterExceptions;
import hobby_garden.hobby_garden_server.common.exception.exceptions.HobbyNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.PostNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.utility.ImageConverter;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import hobby_garden.hobby_garden_server.post.dto.request.CommentsRequest;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.request.LikeDislikeRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.GetCommentsResponse;
import hobby_garden.hobby_garden_server.post.dto.response.PostResponse;
import hobby_garden.hobby_garden_server.post.dto.response.UserPostsResponse;
import hobby_garden.hobby_garden_server.post.model.*;
import hobby_garden.hobby_garden_server.post.repository.MediaRepository;
import hobby_garden.hobby_garden_server.post.repository.PostRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

//TODO like dislike transaction

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final MediaRepository mediaRepository;
    private final ImageConverter imageConverter = new ImageConverter();

    @Override
    public BaseResponse<CreatePostResponse> createPost(String token, CreatePostRequest request)  {
        try {
            //* check if user exist, first check if token is valid and extract username
            //* then check if user exists in db
            User user = getUserByToken(token);
            System.out.println(token);

            //* check if hobby exists
            List<String> hobbyIds = request.getHobbyTagIds();
            List<Hobby> hobbies = new ArrayList<>(Collections.emptyList());
            for (String hobbyId : hobbyIds) {
                Hobby hobby = hobbyRepository.findById(hobbyId).orElse(null);
                if (hobby == null) {
                    throw new HobbyNotFoundException(Strings.hobbyNotFound);
                } else {
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
            post.setImages(new ArrayList<>(Collections.emptyList()));

            //* check is there any media
            List<Media> medias = new ArrayList<>(Collections.emptyList());
            if (request.getImageBase64s() != null) {
                for (String base64String : request.getImageBase64s()) {
                    Media media = new Media();

                    // TODO: 2021-10-13 set media properties
                    media.setFileName("image");
                    media.setFileType("image");

                    try {
                        BufferedImage image = imageConverter.base64StringToImage(base64String);
                        media.setBufferedImage(String.valueOf(image));

                        //* add media to post
                        medias.add(media);

                        mediaRepository.save(media);
                    } catch (IOException e) {
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
            } catch (Exception e) {
                throw new UnknownException(Strings.errorOccurWhileCreatingPost + " " + e.getMessage());
            }
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }

    @Override
    public BaseResponse<String> likePost(String token, LikeDislikeRequest request) {
        try {
            //* check if user exist, first check if token is valid and extract username
            User user = getUserByToken(token);

            //* check if post exists
            Post post = getPostById(request.getPostId());

            //* check user disliked this post
            boolean isUserDislikedPost = postRepository.checkUserDislikedPost(user.getUserId(), post.getPostId());
            if (isUserDislikedPost) {
                //* if user disliked this post, then remove dislike
                postRepository.undislikePost(user.getUserId(), post.getPostId());
            }

            //* check if user already liked this post
            List<Likes> likes = post.getLikes();
            assert likes != null;
            for (Likes like : likes) {
                if (like.getUser().getUserId().equals(user.getUserId())) {
                    //* if user already liked this post, then remove like
                    postRepository.unlikePost(user.getUserId(), post.getPostId());
                    return new BaseResponse<>(true, Strings.userRemoveLike, null);
                }
            }

            try {
                postRepository.likePost(user.getUserId(), post.getPostId(), LocalDateTime.now());
                //* if user already liked this post, then remove like
                return new BaseResponse<>(true, Strings.userLikedThePost, null);
            } catch (Exception e) {
                throw new UnknownException(Strings.errorWhileLikePost + " " + e.getMessage());
            }
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }

    @Override
    public BaseResponse<String> dislikePost(String token, LikeDislikeRequest request) {
        try {
            //* get user
            User user = getUserByToken(token);

            //* check if post exists
            Post post = getPostById(request.getPostId());

            //* check user liked this post
            boolean isUserLikedThisPost = postRepository.checkUserLikedPost(user.getUserId(), post.getPostId());
            if (isUserLikedThisPost) {
                //* if user like this post, then remove like
                postRepository.unlikePost(user.getUserId(), post.getPostId());
            }

            //* check if user already disliked this post
            List<Dislikes> dislikes = post.getDislikes();
            assert dislikes != null;
            for (Dislikes dislike : dislikes) {
                if (dislike.getUser().getUserId().equals(user.getUserId())) {
                    //* if user already liked this post, then remove like
                    postRepository.undislikePost(user.getUserId(), post.getPostId());
                    return new BaseResponse<>(true, Strings.userRemoveDislike, null);
                }
            }

            //* user didn't like this post, then add dislike

            try {
                postRepository.dislikePost(user.getUserId(), post.getPostId(), LocalDateTime.now());
                return new BaseResponse<>(true, Strings.userDislikedThePost, null);
            } catch (Exception e) {
                throw new UnknownException(Strings.errorWhileDislikePost + " " + e.getMessage());
            }
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }

    }

    @Override
    public BaseResponse<String> commentPost(String token, CommentsRequest request) {
        try {
            //* get user, check if user exists
            User user = getUserByToken(token);

            //* check if post exists
            Post post = getPostById(request.getPostId());

            try {
                //* save comment
                postRepository.commentPost(user.getUserId(), post.getPostId(), request.getText(), LocalDateTime.now());
                return new BaseResponse<>(true, Strings.commentedPost, null);
            } catch (Exception e) {
                throw new UnknownException(Strings.errorOccurWhileCommentingPost + " " + e.getMessage());
            }
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }

    @Override
    public BaseResponse<List<UserPostsResponse>> getUserPosts(String token, String username) {
        try {
            //* get user, check if user exists
            User user = getUserByToken(token);

            //* get user posts
            List<Post> posts = user.getPosts();

            //* create response
            List<UserPostsResponse> userPostsResponses = new ArrayList<>(Collections.emptyList());

            //* add posts to response
            for (Post post : posts) {

                UserPostsResponse userPostsResponse = new UserPostsResponse();

                userPostsResponse.setPostId(post.getPostId());
                userPostsResponse.setCreatorName(post.getAuthor().getUsername());
                userPostsResponse.setTitle(post.getTitle());
//            userPostsResponse.setContent(post.getContent());
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
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }

    }

    @Override
    public BaseResponse<List<GetCommentsResponse>> getCommentsOfPost(String token, String postId) {
        try {
            //* check user
            getUserByToken(token);

            //* check if post exists
            Post post = getPostById(postId);

            //* get comments
            List<Comments> comments = post.getComments();

            //* create response
            List<GetCommentsResponse> getCommentsResponses = new ArrayList<>(Collections.emptyList());
            for (Comments comment : comments) {
                GetCommentsResponse getCommentsResponse = new GetCommentsResponse();

                // comment properties
                getCommentsResponse.setCommentId(comment.getId());
                getCommentsResponse.setCommentText(comment.getText());
                getCommentsResponse.setCommentDate(comment.getDate());

                // user properties
                User user = comment.getUser();
                GetCommentsResponse.GetCommentsUser getCommentsUser = new GetCommentsResponse.GetCommentsUser();
                getCommentsUser.setUserId(user.getUserId());
                getCommentsUser.setUsername(user.getUsername());
                getCommentsUser.setFirstNameLastName(user.getFirstNameLastName());
                getCommentsUser.setEmail(user.getEmail());
                getCommentsUser.setCreatedAt(user.getCreatedAt());

                //* set user to comment and add comment to response
                getCommentsResponse.setUser(getCommentsUser);
                getCommentsResponses.add(getCommentsResponse);

            }

            System.out.println(getCommentsResponses);


            return new BaseResponse<>(true, Strings.commentsFetched, getCommentsResponses);
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }

    @Override
    public BaseResponse<PostResponse> getPostByPostId(String token, String postId){
        try {
            //* check user
            getUserByToken(token);

            System.out.println(token);
            System.out.println(postId);

            //* check if post exists
            Post post = getPostById(postId);

            //* create response
            PostResponse postResponse = new PostResponse();

            //* set post properties
            postResponse.setPostId(post.getPostId());
            postResponse.setCreatorName(post.getAuthor().getUsername());
            postResponse.setTitle(post.getTitle());
            postResponse.setContent(post.getContent());
            postResponse.setLikes(post.getLikes().size());
            postResponse.setDislikes(post.getDislikes().size());
            postResponse.setComments(post.getComments().size());
            postResponse.setCreatedAt(post.getCreatedAt());
            postResponse.setUpdatedAt(post.getUpdatedAt());
            //postResponse.setImages(post.getMedia());


            //* return response
            return new BaseResponse<>(true, Strings.postsFound, postResponse);
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }


    //* helper methods, extract user from token, check if user exists, etc.
    private User getUserByToken(String token) {
        String username = jwtService.extractUserNameWithBearer(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }


    private Post getPostById(String postId){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new PostNotFoundException(Strings.postNotFound);
        }
        return post.get();
    }
}
