package acc.inzynierka.modelsDTO;

import acc.inzynierka.models.Flashcard;
import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link Flashcard} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlashcardDto implements Serializable {
    private String expOriginal;
    private String expTranslation;
    private String expDescription;
    private String imageUrl;
    private String imageName;
}