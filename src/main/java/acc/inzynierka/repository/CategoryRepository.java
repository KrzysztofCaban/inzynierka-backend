package acc.inzynierka.repository;

import acc.inzynierka.models.Category;
import acc.inzynierka.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
