package acc.inzynierka.repository;

import acc.inzynierka.models.Status;
import acc.inzynierka.models.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(EStatus status);
}
