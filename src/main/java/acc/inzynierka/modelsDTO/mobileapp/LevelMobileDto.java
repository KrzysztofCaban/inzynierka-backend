package acc.inzynierka.modelsDTO.mobileapp;

import acc.inzynierka.models.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Level} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LevelMobileDto implements Serializable {
    private Long id;
    private String name;
    private int difficulty;
    private int bestResult;
}