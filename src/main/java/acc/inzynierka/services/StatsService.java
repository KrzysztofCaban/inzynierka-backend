package acc.inzynierka.services;

import acc.inzynierka.modelsDTO.CourseStatsAllDto;
import acc.inzynierka.modelsDTO.CourseStatsDto;
import acc.inzynierka.modelsDTO.newUsersPerDay;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {

    @Autowired
    CourseRepository courseRepository;

    public List<CourseStatsDto> getStatsForAuthor() {
        Long adminID = UserUtil.getUser();
        return courseRepository.getCoursesStatsForOneAuthor(adminID);
    }

    public List<CourseStatsAllDto> getStatsForAllAuthors() {
        return courseRepository.getCoursesStatsForAllAuthors();
    }

    public List<newUsersPerDay> getNewUsersPerDay(Long courseId) {
        return courseRepository.getNewUsersPerDay(courseId);
    }
}
