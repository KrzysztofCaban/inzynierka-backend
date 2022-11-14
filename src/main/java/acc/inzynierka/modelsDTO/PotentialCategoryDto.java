package acc.inzynierka.modelsDTO;

import acc.inzynierka.models.PotentialCategory;
import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link PotentialCategory} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PotentialCategoryDto implements Serializable {
    private Long id;
    private String name;
    private String authorLogin;
}