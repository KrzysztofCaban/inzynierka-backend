package acc.inzynierka.services;

import acc.inzynierka.models.Image;
import acc.inzynierka.modelsDTO.ImageDto;
import acc.inzynierka.repository.ImageRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public List<ImageDto> getAllImages(String category){
        List<Image> imageList = imageRepository.findByCategory(category);
        return ObjectMapperUtil.mapToDTO(imageList, ImageDto.class);
    }

}
