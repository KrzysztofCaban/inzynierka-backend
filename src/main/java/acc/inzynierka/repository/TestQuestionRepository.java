package acc.inzynierka.repository;

import acc.inzynierka.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestQuestionRepository extends JpaRepository<RefreshToken, Long> {
}
