package acc.inzynierka.repository;

import acc.inzynierka.models.Followed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowedRepository extends JpaRepository<Followed, Long> {
}
