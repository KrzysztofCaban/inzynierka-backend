package acc.inzynierka.services;

import acc.inzynierka.exception.CategoryNotFoundException;
import acc.inzynierka.exception.CourseAlreadyExistsException;
import acc.inzynierka.exception.CourseNotFoundException;
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
import acc.inzynierka.security.services.UserDetailsImpl;
import acc.inzynierka.utils.objectMapperUtil;
import acc.inzynierka.utils.userUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<CourseDto> getAllCourses(){
        return objectMapperUtil.mapToDTO(courseRepository.findAll(), CourseDto.class);
    }

    public List<CourseDto> getAllAdminCourses(User admin) {
        return objectMapperUtil.mapToDTO(courseRepository.findAllByAuthor(admin), CourseDto.class);
    }

    public CourseDto getCourseByName(String name){
        Course course = courseRepository.findByName(name)
                .orElseThrow(() -> new CourseNotFoundException(name, "Nie znaleziono kursu"));

        return (CourseDto) objectMapperUtil.mapToDTOSingle(course, CourseDto.class);
    }

    public void deleteCourseById(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id, "Nie znaleziono kursu"));

        courseRepository.delete(course);
    }

    public void addCourse(CourseRequest courseRequest) throws RuntimeException {
        Optional checkIfExists = courseRepository.findByName(courseRequest.getName());
        if(checkIfExists.isPresent()){
            throw new CourseAlreadyExistsException(courseRequest.getName(), "Kurs już istnieje");
        }
        Course newCourse = new Course();
        newCourse.setName(courseRequest.getName());
        newCourse.setDescription(courseRequest.getDescription());
        newCourse.setCreated(Timestamp.from(Instant.now()));
        newCourse.setModified(Timestamp.from(Instant.now()));

        User author = userRepository.findById(userUtil.getUser()).get();
        newCourse.setAuthor(author);

        newCourse.setStatus(statusRepository.findByName(courseRequest.getStatusName()).get());
        newCourse.setCategory(categoryRepository.findByName(courseRequest.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException(courseRequest.getCategoryName(),
                        "Nie znaleziono podanej kategorii w bazie")));

        courseRepository.save(newCourse);
    }

    public void editCourse(Long id,CourseRequest courseRequest) throws RuntimeException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id, "Nie znaleziono kursu"));

        Optional checkIfExists = courseRepository.findByName(courseRequest.getName());
        if(checkIfExists.isPresent()){
            throw new CourseAlreadyExistsException(courseRequest.getName(), "Podana nazwa kursu jest już w użyciu");
        }

        course.setName(courseRequest.getName());
        System.out.println(courseRequest.getDescription());
        course.setDescription(courseRequest.getDescription());
        course.setModified(Timestamp.from(Instant.now()));
        course.setStatus(statusRepository.findByName(courseRequest.getStatusName()).get());
        course.setCategory(categoryRepository.findByName(courseRequest.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException(courseRequest.getCategoryName(),
                        "Nie znaleziono podanej kategorii w bazie")));

        courseRepository.save(course);
    }

    public StatusCategoriesResponse getStatusAndCategories(){
        StatusCategoriesResponse scResponse = new StatusCategoriesResponse();
        scResponse.setStatusList(objectMapperUtil.mapToDTO(statusRepository.findAll(), StatusDto.class));
        scResponse.setCategoryList(objectMapperUtil.mapToDTO(categoryRepository.findAll(), CategoryDto.class));

        return scResponse;
    }
}
