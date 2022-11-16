package acc.inzynierka.modelsDTO.mobileapp;

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
public class FlashcardMobileDto implements Serializable {
    private String expOriginal;
    private String expTranslation;
    private String expDescription;
    private String imageUrl;
    private String imageName;
}