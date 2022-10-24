package acc.inzynierka.controllers;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.CourseDto;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.security.services.UserDetailsImpl;
import acc.inzynierka.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        return new ResponseEntity<>(
            courseService.getAllCourses()
            ,HttpStatus.OK);
    }

    @GetMapping(value = {"admin","admin/{id}"})
    public ResponseEntity<?> getAllAdminCourses(@PathVariable Optional<Long> id){
        Long adminId;
        if (id.isPresent()) {
            adminId = id.get();
        } else {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            adminId = userDetails.getId();
        }
        User admin = userRepository.findById(adminId).orElse(null);
        if (admin == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nie znaleziono administratora"));
        }
        if(admin.getRoles()
                .stream()
                .filter(r -> r.getName().equals(ERole.ROLE_ADMIN))
                .findAny()
                .isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Użytkownik nie jest administratorem"));
        }
        return new ResponseEntity<>(
            courseService.getAllAdminCourses(admin)
            ,HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getCourseByName(@PathVariable String name){
        CourseDto course = courseService.getCourseByName(name);
        if (course == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nie znaleziono kursu"));
        }
        return new ResponseEntity<>(
                course
                ,HttpStatus.OK);
    }

    @GetMapping(value = {"delete/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (courseService.deleteCourseById(id)){
            return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto kurs"));
        }else{
            return ResponseEntity.ok().body(new MessageResponse("Nie znaleziono kursu"));
        }


    }







}
