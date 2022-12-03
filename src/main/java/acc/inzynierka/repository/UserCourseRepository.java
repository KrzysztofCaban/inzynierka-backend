package acc.inzynierka.repository;

import acc.inzynierka.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    boolean existsByUser_IdAndCourse_Id(Long Id, Long id);


    Optional<UserCourse> findByUser_IdAndCourse_Id(Long Id, Long id);

    List<UserCourse> findByUserId(Long Id);

}
