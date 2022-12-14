package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.payload.request.mobile.UserResultRequest;
import acc.inzynierka.services.mobileapp.TestQuestionMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/level/{levelID}/test")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class TestQuestionMobileController {

    @Autowired
    TestQuestionMobileService testQuestionMobileService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllFlashcards(@PathVariable Long levelID) {
        return new ResponseEntity<>(
                testQuestionMobileService.getAllTestQuestions(levelID),
                HttpStatus.OK
        );
    }

    @PostMapping(value = {"result"})
    public ResponseEntity<?> saveResults(@RequestBody UserResultRequest userResultRequest) {
        return new ResponseEntity<>(
                testQuestionMobileService.addUserResult(userResultRequest),
                HttpStatus.OK
        );
    }
}
