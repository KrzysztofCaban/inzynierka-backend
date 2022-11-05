package acc.inzynierka.repository;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.Flashcard;
import acc.inzynierka.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    Optional<Flashcard> findByExpOriginal(String expOriginal);
}
