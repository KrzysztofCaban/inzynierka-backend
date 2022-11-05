package acc.inzynierka.controllers;

import acc.inzynierka.payload.request.ExerciseRequest;
import acc.inzynierka.payload.request.FlashcardRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.ExerciseService;
import acc.inzynierka.services.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course/{courseID}/level/{levelID}/exercise")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllExercises(@PathVariable Long levelID){
        return new ResponseEntity<>(
                exerciseService.getAllExercises(levelID),
                HttpStatus.OK
        );
    }

    @GetMapping(value = {"{exerciseId}"})
    public ResponseEntity<?> getExercise(@PathVariable Long exerciseId){
        return new ResponseEntity<>(
                exerciseService.getExerciseById(exerciseId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = {"delete/{exerciseId}"})
    public ResponseEntity<?> deleteExerciseById(@PathVariable Long exerciseId) {

        exerciseService.deleteExerciseById(exerciseId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto ćwiczenie"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createExercise(@PathVariable Long levelID , @Valid @RequestBody ExerciseRequest exerciseRequest) {

        return new ResponseEntity<>(exerciseService.addExercise(levelID ,exerciseRequest), HttpStatus.CREATED);
    }



    @PatchMapping(value = {"edit/{exerciseID}"})
    public ResponseEntity<?> editExercise(@PathVariable Long levelID, @PathVariable Long exerciseID, @Valid @RequestBody ExerciseRequest exerciseRequest) {
        exerciseService.editExercise(levelID, exerciseID, exerciseRequest);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano ćwiczenie"));
    }
}

