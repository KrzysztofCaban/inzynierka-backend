package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.ExerciseDto;
import acc.inzynierka.modelsDTO.FlashcardDto;
import lombok.Data;

@Data
public class ExerciseResponse {
    private ExerciseDto exercise;
    private String message;
}
