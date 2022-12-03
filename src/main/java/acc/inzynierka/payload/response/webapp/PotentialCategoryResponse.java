package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.PotentialCategoryDto;
import lombok.Data;

@Data
public class PotentialCategoryResponse {
    private PotentialCategoryDto category;
    private String message;
}
