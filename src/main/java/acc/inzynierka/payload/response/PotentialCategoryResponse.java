package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.PotentialCategoryDto;
import lombok.Data;

@Data
public class PotentialCategoryResponse {
    private PotentialCategoryDto category;
    private String message;
}
