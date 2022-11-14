package acc.inzynierka.repository;

import acc.inzynierka.models.PotentialCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotentialCategoryRepository extends JpaRepository<PotentialCategory, Long> {
    boolean existsByName(String name);
}