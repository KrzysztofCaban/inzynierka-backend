package acc.inzynierka.modelsDTO.webapp;

import acc.inzynierka.models.PotentialCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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