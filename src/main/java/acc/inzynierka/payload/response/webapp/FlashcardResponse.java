package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.FlashcardDto;
import lombok.Data;

@Data
public class FlashcardResponse {
    private FlashcardDto flashcard;
    private String message;
}
