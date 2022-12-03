package acc.inzynierka.modelsDTO.webapp;

import acc.inzynierka.models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Image} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageDto implements Serializable {
    private String url;
    private String name;
    private String categoryName;
}