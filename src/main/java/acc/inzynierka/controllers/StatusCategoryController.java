package acc.inzynierka.controllers;

import acc.inzynierka.modelsDTO.CategoryDto;
import acc.inzynierka.modelsDTO.StatusDto;
import acc.inzynierka.payload.request.CategoryRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.CategoryService;
import acc.inzynierka.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/StatusCategory")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class StatusCategoryController {

    @Autowired
    StatusService statusService;

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "statuses")
    public ResponseEntity<?> getStatuses() {
        List<StatusDto> statusDtoList = statusService.getStatuses();
        return new ResponseEntity<>(statusDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "categories")
    public ResponseEntity<?> getCategories() {
        List<CategoryDto> categoryDtoList = categoryService.getCategories();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "addCategory")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(
                categoryService.addCategory(categoryRequest),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "editCategory/{categoryID}")
    public ResponseEntity<?> editCategory(@PathVariable Long categoryID, @RequestBody CategoryRequest categoryRequest) {
        categoryService.editCategory(categoryID, categoryRequest);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano kategorię"));
    }


}
