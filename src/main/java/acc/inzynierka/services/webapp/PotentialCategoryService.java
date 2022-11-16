package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.potentialCategory.PotentialCategoryAlreadyExistsException;
import acc.inzynierka.exception.potentialCategory.PotentialCategoryNotFoundException;
import acc.inzynierka.models.PotentialCategory;
import acc.inzynierka.models.User;
import acc.inzynierka.modelsDTO.webapp.PotentialCategoryDto;
import acc.inzynierka.payload.request.webapp.CategoryRequest;
import acc.inzynierka.payload.response.webapp.PotentialCategoryResponse;
import acc.inzynierka.repository.PotentialCategoryRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotentialCategoryService {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    private PotentialCategoryRepository potentialCategoryRepository;

    public List<PotentialCategoryDto> getAllPotentialCategories() {
        return ObjectMapperUtil.mapToDTO(potentialCategoryRepository.findAll(), PotentialCategoryDto.class);
    }

    public PotentialCategoryResponse addPotentialCategory(CategoryRequest categoryRequest) {
        categoryService.checkIfExistsByName(categoryRequest.getName());

        if (potentialCategoryRepository.existsByName(categoryRequest.getName())) {
            throw new PotentialCategoryAlreadyExistsException();
        }

        PotentialCategory potentialCategory = new PotentialCategory();
        User author = userService.findById(UserUtil.getUser());

        potentialCategory.setName(categoryRequest.getName());
        potentialCategory.setAuthor(author);

        PotentialCategory savedPotentialCategory = potentialCategoryRepository.save(potentialCategory);

        PotentialCategoryResponse potentialCategoryResponse = new PotentialCategoryResponse();
        potentialCategoryResponse.setCategory((PotentialCategoryDto) ObjectMapperUtil.mapToDTOSingle(savedPotentialCategory, PotentialCategoryDto.class));
        potentialCategoryResponse.setMessage("Propozycja dodania nowej kategorii została dodana");

        return potentialCategoryResponse;

    }

    public void acceptPotentialCategory(Long id) {
        PotentialCategory potentialCategory = findById(id);

        categoryService.checkIfExistsByName(potentialCategory.getName());

        categoryService.savePotentialCategoryAsCategory(potentialCategory);

        potentialCategoryRepository.delete(potentialCategory);
    }

    public void deletePotentialCategory(Long id) {
        PotentialCategory potentialCategory = findById(id);

        potentialCategoryRepository.delete(potentialCategory);
    }

    public PotentialCategory findById(Long id) {
        return potentialCategoryRepository.findById(id).orElseThrow(PotentialCategoryNotFoundException::new);
    }
}
