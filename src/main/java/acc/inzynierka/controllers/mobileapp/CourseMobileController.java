package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.mobileapp.CourseMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/course")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class CourseMobileController {

    @Autowired
    CourseMobileService courseMobileService;

    @GetMapping(value = {"all"})
    public ResponseEntity<?> getAllActiveCourses() {

        return new ResponseEntity<>(
                courseMobileService.getAllCourses(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "myCourses")
    public ResponseEntity<?> getMyCourses() {
        return new ResponseEntity<>(
                courseMobileService.getMyCourses(),
                HttpStatus.OK
        );
    }


    @PostMapping(value = "{courseId}/join")
    public ResponseEntity<?> joinCourse(@PathVariable Long courseId) {
        courseMobileService.joinCourse(courseId);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zapisano się do kursu"));
    }

    @DeleteMapping(value = "{courseId}/leave")
    public ResponseEntity<?> leaveCourse(@PathVariable Long courseId) {
        courseMobileService.leaveCourse(courseId);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie wypisano się z kursu"));
    }
}
