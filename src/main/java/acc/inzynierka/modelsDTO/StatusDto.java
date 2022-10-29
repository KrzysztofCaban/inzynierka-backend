package acc.inzynierka.modelsDTO;

import acc.inzynierka.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link acc.inzynierka.models.Status} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StatusDto implements Serializable {
    private EStatus name;
}