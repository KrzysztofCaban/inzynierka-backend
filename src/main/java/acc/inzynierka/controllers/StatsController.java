package acc.inzynierka.controllers;

import acc.inzynierka.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stats")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping("courses")
    public ResponseEntity<?> getCoursesStats() {

        return new ResponseEntity<>(
                statsService.getStatsForAuthor(),
                HttpStatus.OK
        );
    }

    @GetMapping("coursesforall")
    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> getCoursesStatsForAllAuthors() {

        return new ResponseEntity<>(
                statsService.getStatsForAllAuthors(),
                HttpStatus.OK
        );
    }

    @GetMapping("newusersperday/{courseId}")
    public ResponseEntity<?> getNewUsersPerDay(@PathVariable Long courseId) {

        return new ResponseEntity<>(
                statsService.getNewUsersPerDayWithCreationDate(courseId),
                HttpStatus.OK
        );
    }
}
