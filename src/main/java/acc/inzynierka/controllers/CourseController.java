package acc.inzynierka.controllers;

import acc.inzynierka.exception.user.UserNotFoundException;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.CourseDto;
import acc.inzynierka.payload.request.CourseRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.services.CourseService;
import acc.inzynierka.services.UserService;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "all")
    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(
                courseService.getAllCourses()
                , HttpStatus.OK);
    }

    @GetMapping(value = {"admin", "admin/{id}"})
    public ResponseEntity<?> getAllAdminCourses(@PathVariable Optional<Long> id) {
        Long adminId = id.orElseGet(UserUtil::getUser);

        User admin = userService.findById(adminId);

        if (admin.getRoles()
                .stream()
                .filter(r -> r.getName().equals(ERole.ROLE_ADMIN))
                .findAny()
                .isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Użytkownik nie jest administratorem");
        }
        return new ResponseEntity<>(
                courseService.getAllAdminCourses(admin)
                , HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        CourseDto course = courseService.getCourseById(id);

        return new ResponseEntity<>(
                course
                , HttpStatus.OK);
    }

    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {

        courseService.deleteCourseById(id);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto kurs"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRequest courseRequest) {

        return new ResponseEntity<>(courseService.addCourse(courseRequest), HttpStatus.CREATED);
    }

    @PatchMapping(value = {"edit/{id}"})
    public ResponseEntity<?> editCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest courseRequest) {
        courseService.editCourse(id, courseRequest);
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano kurs"));
    }


}
