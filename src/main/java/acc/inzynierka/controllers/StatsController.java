package acc.inzynierka.controllers;

import acc.inzynierka.modelsDTO.CourseStatsDto;
import acc.inzynierka.modelsDTO.newUsersPerDay;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stats")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class StatsController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("courses")
    public ResponseEntity<?> getCoursesStats() {
        Long adminID = UserUtil.getUser();


        List<CourseStatsDto> courseStatDtos = courseRepository.getCoursesStats(adminID);

        return new ResponseEntity<>(
                courseStatDtos,
                HttpStatus.OK
        );
    }

    @GetMapping("newusersperday/{courseId}")
    public ResponseEntity<?> getNewUsersPerDay(@PathVariable Long courseId) {
        List<newUsersPerDay> newUsersPerDays = courseRepository.getNewUsersPerDay(courseId);
        return new ResponseEntity<>(
                newUsersPerDays,
                HttpStatus.OK
        );
    }
}
