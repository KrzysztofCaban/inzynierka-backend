package acc.inzynierka.modelsDTO.mobileapp;

import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link acc.inzynierka.models.Course} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CourseMobileListDataDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Timestamp created;
    private Timestamp modified;
    private String authorLogin;
    private String categoryName;
    private EStatus statusName;
}