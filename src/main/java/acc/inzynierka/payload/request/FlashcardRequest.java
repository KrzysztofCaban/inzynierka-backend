package acc.inzynierka.payload.request;

import acc.inzynierka.models.Flashcard;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link Flashcard} entity
 */
@Data
public class FlashcardRequest implements Serializable {
    @NotEmpty(message = "Wyrażenie nie może być puste")
    private String expOriginal;
    @NotEmpty(message = "Tłumacznenie nie może być puste")
    private String expTranslation;
    @NotEmpty(message = "Opis nie może być pusty")
    private String expDescription;
    @NotEmpty(message = "Zdjęcie nie może być puste")
    private String imageName;
}