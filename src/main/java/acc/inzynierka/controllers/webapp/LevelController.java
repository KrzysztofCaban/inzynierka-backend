package acc.inzynierka.controllers.webapp;

import acc.inzynierka.modelsDTO.webapp.LevelDto;
import acc.inzynierka.payload.request.webapp.LevelRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.webapp.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course/{courseID}/level/")
@PreAuthorize(value = "hasRole('ROLE_CREATOR') or hasRole('ROLE_ADMIN')")
public class LevelController {
    @Autowired
    LevelService levelService;


    @GetMapping(value = "all")
    public ResponseEntity<List<LevelDto>> getAllLevels(@PathVariable Long courseID) {
        return new ResponseEntity<>(
                levelService.getAllLevels(courseID),
                HttpStatus.OK
        );
    }

    @GetMapping(value = {"{levelId}"})
    public ResponseEntity<?> getLevel(@PathVariable Long levelId) {
        return new ResponseEntity<>(
                levelService.getLevelById(levelId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = {"delete/{levelId}"})
    public ResponseEntity<?> deleteLevelById(@PathVariable Long levelId) {

        levelService.deleteLevelById(levelId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto poziom"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createLevel(@PathVariable Long courseID, @Valid @RequestBody LevelRequest levelRequest) {

        return new ResponseEntity<>(levelService.addLevel(courseID, levelRequest), HttpStatus.CREATED);
    }


    @PatchMapping(value = {"edit/{levelId}"})
    public ResponseEntity<?> editLevel(@PathVariable Long courseID, @PathVariable Long levelId, @Valid @RequestBody LevelRequest levelRequest) {

        return new ResponseEntity<>(levelService.editLevel(courseID, levelId, levelRequest), HttpStatus.OK);
    }

}
