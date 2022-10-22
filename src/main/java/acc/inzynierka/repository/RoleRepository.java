package acc.inzynierka.repository;

import java.util.Optional;

import acc.inzynierka.models.Role;
import acc.inzynierka.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
