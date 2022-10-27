package acc.inzynierka.repository;

import acc.inzynierka.models.Role;
import acc.inzynierka.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
