package acc.inzynierka.services;

import acc.inzynierka.exception.category.CategoryNotFoundException;
import acc.inzynierka.exception.image.ImageAlreadyExistsException;
import acc.inzynierka.models.Image;
import acc.inzynierka.modelsDTO.ImageDto;
import acc.inzynierka.payload.request.ImageRequest;
import acc.inzynierka.payload.response.ImageResponse;
import acc.inzynierka.repository.CategoryRepository;
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
    ImageRepository imageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<ImageDto> getAllImages(String category){
        List<Image> imageList = imageRepository.findByCategory(category);
        return ObjectMapperUtil.mapToDTO(imageList, ImageDto.class);
    }

    public ImageResponse uploadImage(ImageRequest imageRequest, MultipartFile image) throws IOException {
        Optional checkIfExists = imageRepository.findByName(imageRequest.getName());
        if (checkIfExists.isPresent()) {
            throw new ImageAlreadyExistsException();
        }
        Image newImage = new Image();
        newImage.setName(imageRequest.getName());
        newImage.setUrl(blobStorageService.uploadPicture(image).toURL().toString());
        newImage.setCategory(categoryRepository.findByName(imageRequest.getCategoryName())
                .orElseThrow(CategoryNotFoundException::new));

        Image savedImage = imageRepository.save(newImage);

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setImage((ImageDto) ObjectMapperUtil.mapToDTOSingle(savedImage, ImageDto.class));
        imageResponse.setMessage("Pomyślnie dodane zdjęcie");

        return imageResponse;

    }

}
