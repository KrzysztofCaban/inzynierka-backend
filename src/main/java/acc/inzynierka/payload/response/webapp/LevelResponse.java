package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.LevelDto;
import lombok.Data;

@Data
public class LevelResponse {
    private LevelDto level;
    private String message;
}
