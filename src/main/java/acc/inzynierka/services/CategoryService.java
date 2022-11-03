package acc.inzynierka.services;

import acc.inzynierka.models.Category;
import acc.inzynierka.modelsDTO.CategoryDto;
import acc.inzynierka.repository.CategoryRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories(){
        return ObjectMapperUtil.mapToDTO(categoryRepository.findAll(), CategoryDto.class);
    }
}
