package acc.inzynierka.repository;

import acc.inzynierka.models.Followed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowedRepository extends JpaRepository<Followed, Long> {
    boolean existsByUser_IdAndFollowedUser_Login(Long Id, String login);

    Followed findByUser_IdAndFollowedUser_Login(Long Id, String login);


}
