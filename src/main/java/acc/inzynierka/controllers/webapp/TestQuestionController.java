package acc.inzynierka.controllers.webapp;

import acc.inzynierka.payload.request.webapp.TestQuestionRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.webapp.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course/{courseID}/level/{levelID}/testquestion")
@PreAuthorize(value = "hasRole('ROLE_CREATOR') or hasRole('ROLE_ADMIN')")
public class TestQuestionController {
    @Autowired
    TestQuestionService testQuestionService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllTestQuestions(@PathVariable Long levelID) {
        return new ResponseEntity<>(
                testQuestionService.getAllTestQuestions(levelID),
                HttpStatus.OK
        );
    }

    @GetMapping(value = {"{testQuestionId}"})
    public ResponseEntity<?> getTestQuestion(@PathVariable Long testQuestionId) {
        return new ResponseEntity<>(
                testQuestionService.getTestQuestionById(testQuestionId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = {"delete/{testQuestionId}"})
    public ResponseEntity<?> deleteTestQuestionById(@PathVariable Long testQuestionId) {

        testQuestionService.deleteTestQuestionById(testQuestionId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto pytanie testowe"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createTestQuestion(@PathVariable Long levelID, @Valid @RequestBody TestQuestionRequest testQuestionRequest) {

        return new ResponseEntity<>(testQuestionService.addTestQuestion(levelID, testQuestionRequest), HttpStatus.CREATED);
    }


    @PatchMapping(value = {"edit/{testQuestionId}"})
    public ResponseEntity<?> editTestQuestion(@PathVariable Long levelID, @PathVariable Long testQuestionId, @Valid @RequestBody TestQuestionRequest testQuestionRequest) {
        testQuestionService.editTestQuestion(levelID, testQuestionId, testQuestionRequest);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano pytanie testowe"));
    }
}

