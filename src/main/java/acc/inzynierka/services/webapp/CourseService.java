package acc.inzynierka.services.webapp;


import acc.inzynierka.exception.RoleNotFoundCustomException;
import acc.inzynierka.exception.course.CourseAlreadyExistsException;
import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.course.NotCourseCreatorException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.webapp.CourseDto;
import acc.inzynierka.payload.request.webapp.CourseRequest;
import acc.inzynierka.payload.response.webapp.CourseResponse;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.RoleRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserService userService;

    private final StatusService statusService;

    private final CategoryService categoryService;
    private final RoleRepository roleRepository;

    public CourseService(CourseRepository courseRepository, UserService userService, StatusService statusService, CategoryService categoryService, RoleRepository roleRepository) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.statusService = statusService;
        this.categoryService = categoryService;
        this.roleRepository = roleRepository;
    }

    public List<CourseDto> getAllCourses() {
        return ObjectMapperUtil.mapToDTO(courseRepository.findAll(), CourseDto.class);
    }

    public List<CourseDto> getAllAdminCourses(User admin) {
        return ObjectMapperUtil.mapToDTO(courseRepository.findAllByAuthor(admin), CourseDto.class);
    }

    public void checkAccessToCourse(Long courseId) {
        User currentUser = userService.findById(UserUtil.getUser());
        if (!currentUser.getRoles().
                contains(roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(RoleNotFoundCustomException::new))
        ) {
            if (!courseRepository.findById(courseId).get().getAuthor().getId().equals(currentUser.getId())) {
                throw new NotCourseCreatorException();
            }
        }
    }

    public CourseDto getCourseById(Long id) {
        Course course = findById(id);
        checkAccessToCourse(id);
        return (CourseDto) ObjectMapperUtil.mapToDTOSingle(course, CourseDto.class);
    }

    public void deleteCourseById(Long id) {
        Course course = findById(id);
        checkAccessToCourse(id);
        courseRepository.delete(course);
    }

    public CourseResponse addCourse(CourseRequest courseRequest) throws RuntimeException {
        Optional checkIfExists = findByNameOptional(courseRequest.getName());
        if (checkIfExists.isPresent()) {
            throw new CourseAlreadyExistsException();
        }
        Course newCourse = new Course();
        newCourse.setName(courseRequest.getName().toLowerCase());
        newCourse.setDescription(courseRequest.getDescription());
        newCourse.setCreated(Timestamp.from(Instant.now()));
        newCourse.setModified(Timestamp.from(Instant.now()));

        User author = userService.findById(UserUtil.getUser());
        newCourse.setAuthor(author);

        newCourse.setStatus(statusService.findByName(courseRequest.getStatusName()));
        newCourse.setCategory(categoryService.findByName(courseRequest.getCategoryName()));

        Course savedCourse = courseRepository.save(newCourse);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourse((CourseDto) ObjectMapperUtil.mapToDTOSingle(savedCourse, CourseDto.class));
        courseResponse.setMessage("Pomyślnie utworzono kurs");

        return courseResponse;
    }

    public void editCourse(Long id, CourseRequest courseRequest) throws RuntimeException {
        Course course = findById(id);
        checkAccessToCourse(id);
        Optional checkIfExists = findByNameOptional(courseRequest.getName().toLowerCase());
        if (checkIfExists.isPresent() && !course.getName().equalsIgnoreCase(courseRequest.getName())) {
            throw new CourseAlreadyExistsException();
        }

        course.setName(courseRequest.getName().toLowerCase());
        course.setDescription(courseRequest.getDescription());
        course.setModified(Timestamp.from(Instant.now()));
        course.setStatus(statusService.findByName(courseRequest.getStatusName()));
        course.setCategory(categoryService.findByName(courseRequest.getCategoryName().toLowerCase()));

        courseRepository.save(course);
    }

    public Course findById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        return course;
    }

    public Optional findByNameOptional(String name) {
        Optional courseOptional = courseRepository.findByName(name);

        return courseOptional;
    }
}
