package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.category.CategoryAlreadyExistsException;
import acc.inzynierka.exception.category.CategoryNotFoundException;
import acc.inzynierka.models.Category;
import acc.inzynierka.models.PotentialCategory;
import acc.inzynierka.modelsDTO.webapp.CategoryDto;
import acc.inzynierka.payload.request.webapp.CategoryRequest;
import acc.inzynierka.payload.response.webapp.CategoryResponse;
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

    public List<CategoryDto> getCategories() {
        return ObjectMapperUtil.mapToDTO(categoryRepository.findAll(), CategoryDto.class);
    }

    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        checkIfExistsByName(categoryRequest.getName());

        Category newCategory = new Category();
        newCategory.setName(categoryRequest.getName());

        Category savedCategory = categoryRepository.save(newCategory);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategory((CategoryDto) ObjectMapperUtil.mapToDTOSingle(savedCategory, CategoryDto.class));
        categoryResponse.setMessage("Pomyślnie dodano kategorię");
        return categoryResponse;
    }

    public void checkIfExistsByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new CategoryAlreadyExistsException();
        }
    }

    public void editCategory(Long categoryID, CategoryRequest categoryRequest) {
        Category category = findById(categoryID);

        Optional checkIfExists = findByNameOptional(categoryRequest.getName());
        if (checkIfExists.isPresent() && !category.getName().equals(categoryRequest.getName())) {
            throw new CategoryAlreadyExistsException();
        }

        category.setName(categoryRequest.getName());

        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryID) {
        Category category = findById(categoryID);

        categoryRepository.delete(category);
    }


    public Category findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(CategoryNotFoundException::new);

        return category;
    }

    public Optional findByNameOptional(String name) {
        Optional category = categoryRepository.findByName(name);

        return category;
    }

    public Category findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        return category;
    }

    public void savePotentialCategoryAsCategory(PotentialCategory potentialCategory) {
        Category category = new Category();
        category.setName(potentialCategory.getName());

        categoryRepository.save(category);
    }

}
