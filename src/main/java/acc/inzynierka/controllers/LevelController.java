package acc.inzynierka.controllers;

import acc.inzynierka.modelsDTO.LevelDto;
import acc.inzynierka.payload.request.LevelRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.LevelService;
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
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class LevelController {
    @Autowired
    LevelService levelService;


    @GetMapping(value = "all")
    public ResponseEntity<List<LevelDto>> getAllLevels(@PathVariable Long courseID){
        return new ResponseEntity<>(
                levelService.getAllLevels(courseID),
                HttpStatus.OK
        );
    }

    @GetMapping(value = {"{/levelId}"})
    public ResponseEntity<?> getLevel(@PathVariable Long levelId){
        return new ResponseEntity<>(
                levelService.getLevelById(levelId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = {"delete/{levelId}"})
    public ResponseEntity<?> deleteLevelById(@PathVariable Long levelId) {

        levelService.deleteLevelById(levelId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto kurs"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createLevel(@PathVariable Long courseID ,@Valid @RequestBody LevelRequest levelRequest) {

        levelService.addLevel(courseID ,levelRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie utworzono kurs"));
    }



    @PatchMapping(value = {"edit/{levelId}"})
    public ResponseEntity<?> editLevel(@PathVariable Long courseID, @PathVariable Long levelId, @Valid @RequestBody LevelRequest levelRequest) {
        levelService.editLevel(courseID, levelId, levelRequest);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano kurs"));
    }

    //TODO zwracać listę statusów tak jak Kacper chce

}
