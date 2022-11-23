package acc.inzynierka.payload.request;

import acc.inzynierka.models.TestQuestion;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link TestQuestion} entity
 */
@Data
public class TestQuestionRequest implements Serializable {
    @NotEmpty(message = "Pytanie nie może być puste")
    @Size(min = 2, max = 80, message = "Długość pytania powinna zawierać się pomiędzy 2 a 80 znaków")
    private final String question;

    @NotEmpty(message = "Odpowiedź na pytanie nie może być pusta")
    @Size(min = 1, max = 30, message = "Długość odpowiedzi powinna zawierać się pomiędzy 1 a 30 znaków")
    private final String answer;

    @NotEmpty(message = "Zdjęcie nie może być puste")
    private final String imageName;
}