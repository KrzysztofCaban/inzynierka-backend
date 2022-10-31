package acc.inzynierka.modelsDTO;

import acc.inzynierka.models.enums.EStatus;
import lombok.*;

import java.sql.Timestamp;

/**
 * A DTO for the {@link acc.inzynierka.models.Course} entity
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDto{
    private Long id;
    private String name;
    private String description;
    private Timestamp created;
    private Timestamp modified;
    private Long authorId;
    private String authorLogin;
    private String category;
    private EStatus status;
}