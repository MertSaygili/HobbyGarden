package hobby_garden.hobby_garden_server.storage.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.FilterExceptions;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.utility.ImageUtils;
import hobby_garden.hobby_garden_server.post.model.Post;
import hobby_garden.hobby_garden_server.post.repository.PostRepository;
import hobby_garden.hobby_garden_server.storage.model.Image;
import hobby_garden.hobby_garden_server.storage.repository.StorageRepository;
import hobby_garden.hobby_garden_server.user.model.User;
import hobby_garden.hobby_garden_server.user.repository.UserRepository;
import hobby_garden.hobby_garden_server.user.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Override
    public BaseResponse<?> uploadImageToPost(String token, String postId, MultipartFile imageBase64) {
        try {
            //* check user
            getUserByToken(token);

            System.out.println(token);

            Post post = postRepository.findById(postId).orElseThrow(() -> new UnknownException(Strings.postNotFound));

            List<Image> images = post.getImages();
            images.add(uploadImage(imageBase64));
            post.setImages(images);

            postRepository.save(post);

            return new BaseResponse<>(true, Strings.imageUploadedSuccessfully, null);
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getImage(String token, long fileId) {
        try {
            //* check user
            getUserByToken(token);

            return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(downloadImage(fileId));
        }
        catch (Exception e) {
            throw new FilterExceptions(e.getMessage());
        }
    }

    private User getUserByToken(String token) {
        String username = jwtService.extractUserNameWithBearer(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Strings.userNotFound));
    }


    private Image uploadImage(MultipartFile image){
        try{
            Image newImage = Image.builder()
                    .fileName(image.getOriginalFilename())
                    .fileType(image.getContentType()).
                    data(ImageUtils.compressImage(image.getBytes())).build();
            return storageRepository.save(newImage);
        }
        catch (Exception e){
            throw new UnknownException(Strings.errorOccurWhileUploadingImage + " " + e.getMessage());
        }
    }

    private byte[] downloadImage(long fileId){
        try{
            Optional<Image> image = storageRepository.findById(fileId);
            if(image.isEmpty()){
                throw new UnknownException(Strings.imageNotFound);
            }
            return ImageUtils.decompressImage(image.get().getData());
        }
        catch (Exception e){
            throw new UnknownException(Strings.errorWhileDownloadingImage + " " + e.getMessage());
        }
    }
}
