package acc.inzynierka.modelsDTO.mobileapp;

import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * A DTO for the {@link acc.inzynierka.models.Course} entity
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseMobileDto {
    private String name;
    private String description;
    private Timestamp created;
    private Timestamp modified;
    private Long authorId;
    private String authorLogin;
    private String categoryName;
}