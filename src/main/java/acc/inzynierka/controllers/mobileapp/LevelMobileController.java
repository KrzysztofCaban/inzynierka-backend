package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.services.mobileapp.LevelMobileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/level")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class LevelMobileController {

    private final LevelMobileService levelMobileService;

    public LevelMobileController(LevelMobileService levelMobileService) {
        this.levelMobileService = levelMobileService;
    }

    @GetMapping("{courseId}")
    public ResponseEntity<?> getAllCourseLevels(@PathVariable Long courseId) {
        return new ResponseEntity<>(
                levelMobileService.getAllLevels(courseId),
                HttpStatus.OK
        );
    }
}
