package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.FlashcardDto;
import lombok.Data;

@Data
public class FlashcardResponse {
    private FlashcardDto flashcard;
    private String message;
}
