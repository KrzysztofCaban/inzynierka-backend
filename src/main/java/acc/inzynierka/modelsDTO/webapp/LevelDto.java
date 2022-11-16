package acc.inzynierka.modelsDTO.webapp;

import acc.inzynierka.models.Level;
import acc.inzynierka.models.enums.EStatus;
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
public class LevelDto implements Serializable {
    private Long id;
    private String name;
    private int difficulty;
    private EStatus statusName;
    private int exerciseNumber;
    private int flashcardNumber;
    private int testQuestionNumber;
}