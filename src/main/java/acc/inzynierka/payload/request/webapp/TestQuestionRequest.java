package acc.inzynierka.payload.request.webapp;

import acc.inzynierka.models.TestQuestion;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link TestQuestion} entity
 */
@Data
public class TestQuestionRequest implements Serializable {
    @NotEmpty(message = "Pytanie nie może być puste")
    private final String question;
    @NotEmpty(message = "Odpowiedź na pytanie nie może być pusta")
    private final String answer;
    @NotEmpty(message = "Zdjęcie nie może być puste")
    private final String imageName;
}