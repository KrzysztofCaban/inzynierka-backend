package acc.inzynierka.payload.request;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Course} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CourseRequest implements Serializable {
    @NotNull(message = "Nazwa nie może być pusta")
    private String name;
    private String description;
    @NotNull(message = "Nazwa kategori nie może być pusta")
    private String category;
    @NotNull(message = "Nazwa statusu nie może być pusta")
    private EStatus status;
}