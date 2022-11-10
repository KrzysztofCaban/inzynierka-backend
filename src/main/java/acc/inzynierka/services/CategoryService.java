package acc.inzynierka.services;

import acc.inzynierka.exception.category.CategoryAlreadyExistsException;
import acc.inzynierka.exception.category.CategoryNotFoundException;
import acc.inzynierka.models.Category;
import acc.inzynierka.modelsDTO.CategoryDto;
import acc.inzynierka.payload.request.CategoryRequest;
import acc.inzynierka.payload.response.CategoryResponse;
import acc.inzynierka.repository.CategoryRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories(){
        return ObjectMapperUtil.mapToDTO(categoryRepository.findAll(), CategoryDto.class);
    }

    public CategoryResponse addCategory(CategoryRequest categoryRequest){
        Optional checkIfExists = categoryRepository.findByName(categoryRequest.getName());
        if (checkIfExists.isPresent()) {
            throw new CategoryAlreadyExistsException();
        }
        Category newCategory = new Category();
        newCategory.setName(categoryRequest.getName());

        Category savedCategory = categoryRepository.save(newCategory);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategory((CategoryDto) ObjectMapperUtil.mapToDTOSingle(savedCategory, CategoryDto.class));
        categoryResponse.setMessage("Pomyślnie dodano kategorię");
        return categoryResponse;
    }

    public void editCategory(Long categoryID, CategoryRequest categoryRequest){
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(CategoryNotFoundException::new);

        Optional checkIfExists = categoryRepository.findByName(categoryRequest.getName());
        if (checkIfExists.isPresent() && !category.getName().equals(categoryRequest.getName())) {
            throw new CategoryAlreadyExistsException();
        }

        category.setName(categoryRequest.getName());

        categoryRepository.save(category);
    }
}
