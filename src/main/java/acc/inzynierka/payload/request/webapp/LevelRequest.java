package acc.inzynierka.payload.request.webapp;

import acc.inzynierka.models.Level;
import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Level} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LevelRequest implements Serializable {
    @NotNull(message = "Nazwa nie może być pusta")
    private String name;
    @NotNull(message = "Poziom trudności nie może być pusty")
    private int difficulty;
    @NotNull(message = "Nazwa statusu nie może być pusta")
    private EStatus statusName;
}