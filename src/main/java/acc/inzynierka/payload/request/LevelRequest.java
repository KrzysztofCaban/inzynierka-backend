package acc.inzynierka.payload.request;

import acc.inzynierka.models.Level;
import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 2, max = 24, message = "Długość nazwy powinna zawierać się pomiędzy 2 a 24 znaków")
    private String name;

    @Min(value = 0, message = "Poziom musi być większy lub równy 0")
    @Max(value = 9, message = "Poziom musi być mniejszy lub równby 9")
    @NotNull(message = "Poziom trudności nie może być pusty")
    private int difficulty;
    @NotNull(message = "Nazwa statusu nie może być pusta")
    private EStatus statusName;
}