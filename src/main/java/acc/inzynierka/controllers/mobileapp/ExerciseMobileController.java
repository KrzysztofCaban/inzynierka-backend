package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.services.mobileapp.ExerciseMobileService;
import acc.inzynierka.services.mobileapp.FlashcardMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/level/{levelID}/exercise")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class ExerciseMobileController {
    @Autowired
    ExerciseMobileService exerciseMobileService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllFlashcards(@PathVariable Long levelID) {
        return new ResponseEntity<>(
                exerciseMobileService.getAllExercises(levelID),
                HttpStatus.OK
        );
    }
}
