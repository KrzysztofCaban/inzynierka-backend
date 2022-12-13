package acc.inzynierka.services;

import acc.inzynierka.modelsDTO.CourseStatsAllDto;
import acc.inzynierka.modelsDTO.CourseStatsDto;
import acc.inzynierka.modelsDTO.NewUsersPerDayCourseDto;
import acc.inzynierka.modelsDTO.newUsersPerDay;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.services.webapp.CourseService;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    public List<CourseStatsDto> getStatsForAuthor() {
        Long adminID = UserUtil.getUser();
        return courseRepository.getCoursesStatsForOneAuthor(adminID);
    }

    public List<CourseStatsAllDto> getStatsForAllAuthors() {
        return courseRepository.getCoursesStatsForAllAuthors();
    }

    public NewUsersPerDayCourseDto getNewUsersPerDayWithCreationDate(Long courseId) {
        NewUsersPerDayCourseDto newUsersPerDayCourseDto = new NewUsersPerDayCourseDto();
        newUsersPerDayCourseDto.setCreationDate(courseService.findById(courseId).getCreated());
        newUsersPerDayCourseDto.setCourseName(courseService.getCourseById(courseId).getName());
        newUsersPerDayCourseDto.setNewUsersPerDay(getNewUsersPerDay(courseId));
        return newUsersPerDayCourseDto;
    }

    public List<newUsersPerDay> getNewUsersPerDay(Long courseId) {
        return courseRepository.getNewUsersPerDay(courseId);
    }
}
