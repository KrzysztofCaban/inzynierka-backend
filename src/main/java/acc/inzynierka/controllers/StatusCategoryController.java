package acc.inzynierka.controllers;

import acc.inzynierka.modelsDTO.CategoryDto;
import acc.inzynierka.modelsDTO.StatusDto;
import acc.inzynierka.services.CategoryService;
import acc.inzynierka.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/KacperWantedThis")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class StatusCategoryController {

    @Autowired
    StatusService statusService;

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "statuses")
    public ResponseEntity<?> getStatuses(){
        List<StatusDto> statusDtoList = statusService.getStatuses();
        return new ResponseEntity<>(statusDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "categories")
    public ResponseEntity<?> getCategories(){
        List<CategoryDto> categoryDtoList = categoryService.getCategories();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }

}
