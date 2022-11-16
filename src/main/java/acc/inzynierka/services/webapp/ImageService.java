package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.image.ImageAlreadyExistsException;
import acc.inzynierka.exception.image.ImageNotFoundException;
import acc.inzynierka.models.Image;
import acc.inzynierka.modelsDTO.webapp.ImageDto;
import acc.inzynierka.payload.request.webapp.ImageRequest;
import acc.inzynierka.payload.response.webapp.ImageResponse;
import acc.inzynierka.repository.ImageRepository;
import acc.inzynierka.services.BlobStorage.BlobStorageService;
import acc.inzynierka.utils.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final BlobStorageService blobStorageService;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryService categoryService;

    public void checkIfExistsByName(String name) {
        Optional checkIfExists = findByNameOptional(name);
        if (checkIfExists.isPresent()) {
            throw new ImageAlreadyExistsException();
        }
    }

    public Image findByName(String name) {
        Image image = imageRepository.findByName(name)
                .orElseThrow(ImageNotFoundException::new);

        return image;
    }

    public Optional findByNameOptional(String name) {
        Optional imageOptional = imageRepository.findByName(name);

        return imageOptional;
    }

    public List<Image> findByCategory(String category) {
        List<Image> imageList = imageRepository.findByCategoryNameIsContaining(category);

        return imageList;
    }

    public List<ImageDto> getAllImages(String category) {
        List<Image> imageList = findByCategory(category);
        return ObjectMapperUtil.mapToDTO(imageList, ImageDto.class);
    }

    public ImageResponse uploadImage(ImageRequest imageRequest, MultipartFile image) throws IOException {
        checkIfExistsByName(imageRequest.getName());

        Image newImage = new Image();
        newImage.setName(imageRequest.getName());
        newImage.setUrl(blobStorageService.uploadPicture(image).toURL().toString());
        newImage.setCategory(categoryService.findByName(imageRequest.getCategoryName()));

        Image savedImage = imageRepository.save(newImage);

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setImage((ImageDto) ObjectMapperUtil.mapToDTOSingle(savedImage, ImageDto.class));
        imageResponse.setMessage("Pomyślnie dodane zdjęcie");

        return imageResponse;

    }
}
