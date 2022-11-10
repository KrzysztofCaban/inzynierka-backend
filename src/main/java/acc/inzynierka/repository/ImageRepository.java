package acc.inzynierka.repository;

import acc.inzynierka.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCategory(String category);
    Optional<Image> findByName(String name);
}
