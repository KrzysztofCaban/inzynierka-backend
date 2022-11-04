package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.LevelDto;
import lombok.Data;

@Data
public class LevelResponse {
    private LevelDto level;
    private String message;
}
