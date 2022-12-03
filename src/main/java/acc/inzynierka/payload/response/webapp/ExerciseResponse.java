package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.ExerciseDto;
import lombok.Data;

@Data
public class ExerciseResponse {
    private ExerciseDto exercise;
    private String message;
}
