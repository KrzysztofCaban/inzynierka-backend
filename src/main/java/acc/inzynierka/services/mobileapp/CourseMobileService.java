package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.usercourse.UserIsInCourseAlreadyException;
import acc.inzynierka.exception.usercourse.UserIsNotInCourseException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import acc.inzynierka.models.UserCourse;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.modelsDTO.mobileapp.CourseMobileListDataDto;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.UserCourseRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseMobileService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private UserMobileService userMobileService;

    public List<CourseMobileListDataDto> getAllCourses() {
        User user = userMobileService.findById(UserUtil.getUser());
        List<Course> courseList = courseRepository.findAll();
        List<CourseMobileListDataDto> courseMobileListDataDtoList = courseList.stream()
                .filter(course -> course.getStatus().getName().equals(EStatus.STATUS_ACTIVE))
                .map(course -> {
                    return new CourseMobileListDataDto(
                            course.getId(),
                            course.getName(),
                            course.getDescription(),
                            course.getAuthor().getLogin(),
                            course.getCategory().getName(),
                           userCourseRepository.existsByUser_IdAndCourse_Id(user.getId(), course.getId()) );
                })
                .collect(Collectors.toList());
        return courseMobileListDataDtoList;
    }

    public List<CourseMobileListDataDto> getMyCourses() {
        Long userId = UserUtil.getUser();
        User user = userMobileService.findById(userId);

        List<Course> courseList = user.getCourses().stream()
                .map(userCourse -> userCourse.getCourse())
                .filter(course -> course.getStatus().equals(EStatus.STATUS_ACTIVE))
                .collect(Collectors.toList());

        return ObjectMapperUtil.mapToDTO(courseList, CourseMobileListDataDto.class);
    }

    public void joinCourse(Long courseId) {
        Long userId = UserUtil.getUser();

        if (checkIfUserIsInCourse(userId, courseId))
            throw new UserIsInCourseAlreadyException();

        Course course = findById(courseId);
        User user = userMobileService.findById(userId);

        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourse.setJoinDate(Timestamp.from(Instant.now()));

        userCourseRepository.save(userCourse);
    }

    public void leaveCourse(Long courseId) {
        Long userId = UserUtil.getUser();

        if (!courseRepository.existsById(courseId))
            throw new CourseNotFoundException();

        UserCourse userCourse = getUserCourse(userId, courseId);

        userCourseRepository.delete(userCourse);
    }


    public Course findById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        return course;
    }

    public boolean checkIfUserIsInCourse(Long userId, Long courseId) {
        return userCourseRepository.existsByUser_IdAndCourse_Id(userId, courseId);
    }

    public UserCourse getUserCourse(Long userId, Long courseId) {
        UserCourse userCourse = userCourseRepository.findByUser_IdAndCourse_Id(userId, courseId)
                .orElseThrow(UserIsNotInCourseException::new);

        return userCourse;
    }
}
