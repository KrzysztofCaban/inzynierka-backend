package acc.inzynierka.repository;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByAuthor(User user);

    Optional<Course> findByName(String name);

    void deleteByName(String name);


}
