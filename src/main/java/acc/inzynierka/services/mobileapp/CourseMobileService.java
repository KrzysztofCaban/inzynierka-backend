package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.modelsDTO.mobileapp.CourseMobileDto;
import acc.inzynierka.modelsDTO.mobileapp.CourseMobileListDataDto;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.services.webapp.CategoryService;
import acc.inzynierka.services.webapp.StatusService;
import acc.inzynierka.services.webapp.UserService;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseMobileService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CategoryService categoryService;

    public List<CourseMobileListDataDto> getAllCourses() {
        return ObjectMapperUtil.mapToDTO(courseRepository.findAll(), CourseMobileListDataDto.class);
    }

    public CourseMobileDto getCourseById(Long id) {
        Course course = findById(id);

        return (CourseMobileDto) ObjectMapperUtil.mapToDTOSingle(course, CourseMobileDto.class);
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
