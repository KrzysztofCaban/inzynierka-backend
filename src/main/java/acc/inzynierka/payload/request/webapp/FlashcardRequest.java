package acc.inzynierka.payload.request.webapp;

import acc.inzynierka.models.Flashcard;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Flashcard} entity
 */
@Data
public class FlashcardRequest implements Serializable {
    @NotEmpty(message = "Wyrażenie nie może być puste")
    @Size(min = 1, max = 30, message = "Długość odpowiedzi powinna zawierać się pomiędzy 1 a 16 znaków")
    private String expOriginal;

    @NotEmpty(message = "Tłumacznenie nie może być puste")
    @Size(min = 1, max = 30, message = "Długość tłumaczenia powinna zawierać się pomiędzy 1 a 30 znaków")
    private String expTranslation;

    //    @NotEmpty(message = "Opis nie może być pusty")
    @Size(min = 4, max = 80, message = "Długość opisu powinna zawierać się pomiędzy 4 a 80 znaków")
    private String expDescription;

    @NotEmpty(message = "Zdjęcie nie może być puste")
    private String imageName;
}