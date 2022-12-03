package acc.inzynierka.payload.request.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * A DTO for the {@link acc.inzynierka.models.Category} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CategoryRequest implements Serializable {
    @NotEmpty(message = "Nazwa kategorii nie może być pusta")
    @Size(min = 2, max = 16, message = "Długość nazwy powinna zawierać się pomiędzy 2 a 16 znaków")
    private String name;
}
