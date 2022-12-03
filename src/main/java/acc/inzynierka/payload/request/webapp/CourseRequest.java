package acc.inzynierka.payload.request.webapp;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Course} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CourseRequest implements Serializable {
    @NotEmpty(message = "Nazwa nie może być pusta")
    @Size(min = 4, max = 24, message = "Długość nazwy powinna zawierać się pomiędzy 4 a 24 znaków")
    private String name;
    @Size(min = 30, max = 240, message = "Długość opisu powinna zawierać się pomiędzy 2 a 240 znaków")
    private String description;
    @NotEmpty(message = "Nazwa kategori nie może być pusta")
    private String categoryName;
    @NotNull(message = "Nazwa statusu nie może być pusta")
    private EStatus statusName;
}