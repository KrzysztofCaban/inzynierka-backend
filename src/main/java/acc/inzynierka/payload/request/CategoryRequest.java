package acc.inzynierka.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
    private String name;
}
