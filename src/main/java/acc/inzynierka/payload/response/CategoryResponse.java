package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.CategoryDto;
import lombok.Data;

@Data
public class CategoryResponse {
    private CategoryDto category;
    private String message;
}
