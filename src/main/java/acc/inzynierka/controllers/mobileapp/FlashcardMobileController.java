package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.payload.request.webapp.FlashcardRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.mobileapp.FlashcardMobileService;
import acc.inzynierka.services.webapp.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/course/{courseID}/level/{levelID}/flashcard")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class FlashcardMobileController {
    @Autowired
    FlashcardMobileService flashcardMobileService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllFlashcards(@PathVariable Long levelID) {
        return new ResponseEntity<>(
                flashcardMobileService.getAllFlashcards(levelID),
                HttpStatus.OK
        );
    }
}
