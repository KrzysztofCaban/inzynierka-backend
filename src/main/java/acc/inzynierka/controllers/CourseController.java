package acc.inzynierka.controllers;

import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.CourseDto;
import acc.inzynierka.payload.request.CourseRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.payload.response.StatusCategoriesResponse;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.services.CourseService;
import acc.inzynierka.utils.userUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course")
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return new ResponseEntity<>(
                courseService.getAllCourses()
                , HttpStatus.OK);
    }

    @GetMapping(value = {"admin", "admin/{id}"})
    public ResponseEntity<?> getAllAdminCourses(@PathVariable Optional<Long> id) {
        Long adminId = id.orElseGet(userUtil::getUser);

        User admin = userRepository.findById(adminId).orElse(null);
        if (admin == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nie znaleziono administratora"));
        }
        if (admin.getRoles()
                .stream()
                .filter(r -> r.getName().equals(ERole.ROLE_ADMIN))
                .findAny()
                .isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Użytkownik nie jest administratorem"));
        }
        return new ResponseEntity<>(
                courseService.getAllAdminCourses(admin)
                , HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        CourseDto course;
        try {
            course = courseService.getCourseById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return new ResponseEntity<>(
                course
                , HttpStatus.OK);
    }

    @PostMapping(value = {"delete/{id}"})
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        try {
            courseService.deleteCourseById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto kurs"));

    }

    @PostMapping(value = {"add"})
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        try {
            courseService.addCourse(courseRequest);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie utworzono kurs"));
    }

    @GetMapping(value = {"statusAndCategories"})
    public ResponseEntity<?> getStatusAndCategories(){
        StatusCategoriesResponse statusCategoriesResponse;
        try{
            statusCategoriesResponse = courseService.getStatusAndCategories();
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return new ResponseEntity<>(
                statusCategoriesResponse,
                HttpStatus.OK
        );
    }

    @PostMapping(value = {"edit/{id}"})
    public ResponseEntity<?> editCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest courseRequest) {
        try {
            courseService.editCourse(id ,courseRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano kurs"));
    }


}
