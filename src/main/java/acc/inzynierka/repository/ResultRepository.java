package acc.inzynierka.repository;

import acc.inzynierka.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
    boolean existsByUser_IdAndLevel_Id(Long Id, Long id);

    Result findByUser_IdAndLevel_Id(Long Id, Long id);


}
