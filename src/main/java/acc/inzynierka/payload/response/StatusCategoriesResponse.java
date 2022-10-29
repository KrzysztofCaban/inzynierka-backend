package acc.inzynierka.payload.response;

import acc.inzynierka.models.Status;
import acc.inzynierka.modelsDTO.CategoryDto;
import acc.inzynierka.modelsDTO.StatusDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatusCategoriesResponse {
    List<StatusDto> statusList;

    List<CategoryDto> categoryList;
}
