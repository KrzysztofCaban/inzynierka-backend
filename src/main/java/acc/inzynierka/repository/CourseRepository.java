package acc.inzynierka.repository;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import acc.inzynierka.modelsDTO.CourseStatsAllDto;
import acc.inzynierka.modelsDTO.CourseStatsDto;
import acc.inzynierka.modelsDTO.newUsersPerDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByAuthor(User user);

    Optional<Course> findByName(String name);

    void deleteByName(String name);

    @Query(name = "getCoursesStatsForOneAuthor", nativeQuery = true)
    List<CourseStatsDto> getCoursesStatsForOneAuthor(@Param("adminID") Long adminID);

    @Query(name = "getCoursesStatsForAllAuthors", nativeQuery = true)
    List<CourseStatsAllDto> getCoursesStatsForAllAuthors();

    @Query(name = "newUsersPerDay", nativeQuery = true)
    List<newUsersPerDay> getNewUsersPerDay(@Param("courseId") Long courseId);
}
