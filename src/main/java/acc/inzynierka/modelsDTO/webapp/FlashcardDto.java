package acc.inzynierka.modelsDTO.webapp;

import acc.inzynierka.models.Flashcard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Flashcard} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlashcardDto implements Serializable {
    private Long id;
    private String expOriginal;
    private String expTranslation;
    private String expDescription;
    private String imageUrl;
    private String imageName;
}