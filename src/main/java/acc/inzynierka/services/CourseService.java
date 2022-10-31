package acc.inzynierka.services;

import acc.inzynierka.exception.category.CategoryNotFoundException;
import acc.inzynierka.exception.course.CourseAlreadyExistsException;
import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.status.StatusNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import acc.inzynierka.modelsDTO.CategoryDto;
import acc.inzynierka.modelsDTO.CourseDto;
import acc.inzynierka.modelsDTO.StatusDto;
import acc.inzynierka.payload.request.CourseRequest;
import acc.inzynierka.payload.response.StatusCategoriesResponse;
import acc.inzynierka.repository.CategoryRepository;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.StatusRepository;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CourseDto> getAllCourses() {
        return ObjectMapperUtil.mapToDTO(courseRepository.findAll(), CourseDto.class);
    }

    public List<CourseDto> getAllAdminCourses(User admin) {
        return ObjectMapperUtil.mapToDTO(courseRepository.findAllByAuthor(admin), CourseDto.class);
    }


    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        return (CourseDto) ObjectMapperUtil.mapToDTOSingle(course, CourseDto.class);
    }

    public void deleteCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        courseRepository.delete(course);
    }

    public void addCourse(CourseRequest courseRequest) throws RuntimeException {
        Optional checkIfExists = courseRepository.findByName(courseRequest.getName());
        if (checkIfExists.isPresent()) {
            throw new CourseAlreadyExistsException();
        }
        Course newCourse = new Course();
        newCourse.setName(courseRequest.getName());
        newCourse.setDescription(courseRequest.getDescription());
        newCourse.setCreated(Timestamp.from(Instant.now()));
        newCourse.setModified(Timestamp.from(Instant.now()));

        User author = userRepository.findById(UserUtil.getUser()).get();
        newCourse.setAuthor(author);

        newCourse.setStatus(statusRepository.findByName(courseRequest.getStatusName())
                .orElseThrow(StatusNotFoundException::new));
        newCourse.setCategory(categoryRepository.findByName(courseRequest.getCategoryName())
                .orElseThrow(CategoryNotFoundException::new));

        courseRepository.save(newCourse);
    }

    public void editCourse(Long id, CourseRequest courseRequest) throws RuntimeException {
        Course course = courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        Optional checkIfExists = courseRepository.findByName(courseRequest.getName());
        if (checkIfExists.isPresent() && !course.getName().equals(courseRequest.getName())) {
            throw new CourseAlreadyExistsException();
        }

        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        course.setModified(Timestamp.from(Instant.now()));
        course.setStatus(statusRepository.findByName(courseRequest.getStatusName())
                .orElseThrow(StatusNotFoundException::new));
        course.setCategory(categoryRepository.findByName(courseRequest.getCategoryName())
                .orElseThrow(CategoryNotFoundException::new));

        courseRepository.save(course);
    }

    public StatusCategoriesResponse getStatusAndCategories() {
        StatusCategoriesResponse scResponse = new StatusCategoriesResponse();
        scResponse.setStatusList(ObjectMapperUtil.mapToDTO(statusRepository.findAll(), StatusDto.class));
        scResponse.setCategoryList(ObjectMapperUtil.mapToDTO(categoryRepository.findAll(), CategoryDto.class));

        return scResponse;
    }
}
