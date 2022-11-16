package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.CategoryDto;
import lombok.Data;

@Data
public class CategoryResponse {
    private CategoryDto category;
    private String message;
}
