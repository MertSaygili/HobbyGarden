package hobby_garden.hobby_garden_server.storage.service;

import hobby_garden.hobby_garden_server.common.constants.Strings;
import hobby_garden.hobby_garden_server.common.dto.BaseResponse;
import hobby_garden.hobby_garden_server.common.exception.exceptions.UnknownException;
import hobby_garden.hobby_garden_server.common.utility.ImageUtils;
import hobby_garden.hobby_garden_server.post.model.Post;
import hobby_garden.hobby_garden_server.post.repository.PostRepository;
import hobby_garden.hobby_garden_server.storage.model.Image;
import hobby_garden.hobby_garden_server.storage.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final PostRepository postRepository;

    @Override
    public BaseResponse<?> uploadImageToPost(String postId, MultipartFile imageBase64) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UnknownException(Strings.postNotFound));

        List<Image> images  = post.getImages();
        images.add(uploadImage(imageBase64));
        post.setImages(images);

        postRepository.save(post);

        return new BaseResponse<>(true, Strings.imageUploadedSuccessfully, null);
    }

    @Override
    public BaseResponse<?> getImage(String fileName) {
        return new BaseResponse<>(true, Strings.imageDownloadedSuccessfully, downloadImage(fileName));
    }

    private Image uploadImage(MultipartFile image){
        try{
            Image newImage = Image.builder()
                    .fileName(image.getOriginalFilename())
                    .fileType(image.getContentType()).
                    data(ImageUtils.compressImage(image.getBytes())).build();
            System.out.println(newImage.toString());
            System.out.println(newImage.getFileName());
            return storageRepository.save(newImage);
        }
        catch (Exception e){
            throw new UnknownException(Strings.errorOccurWhileUploadingImage + " " + e.getMessage());
        }
    }

    private byte[] downloadImage(String fileName){
        try{
            Optional<Image> image = storageRepository.findImageByName(fileName);
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
