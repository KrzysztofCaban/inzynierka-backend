package acc.inzynierka.controllers.webapp;


import acc.inzynierka.modelsDTO.webapp.PotentialCategoryDto;
import acc.inzynierka.payload.request.webapp.CategoryRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.webapp.PotentialCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/potentialCategory")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class PotentialCategoryController {

    @Autowired
    PotentialCategoryService potentialCategoryService;

    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @GetMapping(value = "all")
    public ResponseEntity<List<PotentialCategoryDto>> getAllPotentialCategories() {
        return new ResponseEntity<>(
                potentialCategoryService.getAllPotentialCategories(),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addPotentialCategory(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(potentialCategoryService.addPotentialCategory(categoryRequest),
                HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @PostMapping(value = "acceptPotentialCategory/{potentialCatId}")
    public ResponseEntity<?> acceptPotentialCategory(@PathVariable Long potentialCatId) {
        potentialCategoryService.acceptPotentialCategory(potentialCatId);

        return ResponseEntity.ok()
                .body(new MessageResponse("Pomyślnie zaakceptowano prośbę dodania kategorii. Kategoria dodana"));
    }

    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @PostMapping(value = "deletePotentialCategory/{potentialCatId}")
    public ResponseEntity<?> deletePotentialCategory(@PathVariable Long potentialCatId) {
        potentialCategoryService.deletePotentialCategory(potentialCatId);

        return ResponseEntity.ok()
                .body(new MessageResponse("Pomyślnie usunięto prośbę dodania kategorii"));
    }
}
