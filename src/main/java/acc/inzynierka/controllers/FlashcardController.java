package acc.inzynierka.controllers;

import acc.inzynierka.payload.request.FlashcardRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course/{courseID}/level/{levelID}/flashcard")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class FlashcardController {
    @Autowired
    FlashcardService flashcardService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllFlashcards(@PathVariable Long levelID) {
        return new ResponseEntity<>(
                flashcardService.getAllFlashcards(levelID),
                HttpStatus.OK
        );
    }

    @GetMapping(value = {"{flashcardId}"})
    public ResponseEntity<?> getFlashcard(@PathVariable Long flashcardId) {
        return new ResponseEntity<>(
                flashcardService.getFlashcardById(flashcardId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = {"delete/{flashcardId}"})
    public ResponseEntity<?> deleteFlashcardById(@PathVariable Long flashcardId) {

        flashcardService.deleteFlashcardById(flashcardId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto fiszkę"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createFlashcard(@PathVariable Long levelID, @Valid @RequestBody FlashcardRequest flashcardRequest) {

        return new ResponseEntity<>(flashcardService.addFlashcard(levelID, flashcardRequest), HttpStatus.CREATED);
    }


    @PatchMapping(value = {"edit/{flashcardID}"})
    public ResponseEntity<?> editFlashcard(@PathVariable Long levelID, @PathVariable Long flashcardID, @Valid @RequestBody FlashcardRequest flashcardRequest) {
        flashcardService.editFlashcard(levelID, flashcardID, flashcardRequest);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano fiszkę"));
    }
}
