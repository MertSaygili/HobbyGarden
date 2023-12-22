package hobby_garden.hobby_garden_server.post.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.HobbyNotFoundException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UserNotFoundException;
import hobby_garden.hobby_garden_server.common.utility.ImageConverter;
import hobby_garden.hobby_garden_server.hobby.model.Hobby;
import hobby_garden.hobby_garden_server.hobby.repository.HobbyRepository;
import hobby_garden.hobby_garden_server.post.dto.request.CreatePostRequest;
import hobby_garden.hobby_garden_server.post.dto.response.CreatePostResponse;
import hobby_garden.hobby_garden_server.post.model.Media;
import hobby_garden.hobby_garden_server.post.model.Post;
import hobby_garden.hobby_garden_server.post.repository.MediaRepository;
import hobby_garden.hobby_garden_server.post.repository.PostRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public BaseResponse<CreatePostResponse> createPost(CreatePostRequest request)  {
        //* check if user exist, first check if token is valid and extract username
        //* then check if user exists in db
        String username = jwtService.extractUserName(request.getUserToken());
        if (username == null) {
            throw new UserNotFoundException(Strings.userNotFound);
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(Strings.userNotFound);
        }

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
        post.setAuthor(user.get());
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

        //* save post
        try {
            postRepository.save(post);

            //* return response
            return new BaseResponse<>(true, Strings.postCreated, null);
        }
        catch (Exception e){
            throw new UnknownException(Strings.errorOccurWhileCreatingPost + " " + e.getMessage());
        }
    }
}
