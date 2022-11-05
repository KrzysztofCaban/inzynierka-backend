package acc.inzynierka.payload.request;

import acc.inzynierka.models.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link Exercise} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseRequest implements Serializable {
    @NotEmpty(message = "Pytanie nie może być puste")
    private String question;
    @NotEmpty(message = "Wyrażenie nie może być puste")
    private String expression;
    @NotEmpty(message = "Błedna odpowiedź 1 nie może być puste")
    private String bad_answer1;
    @NotEmpty(message = "Błedna odpowiedź 2 nie może być puste")
    private String bad_answer2;
    @NotEmpty(message = "Błedna odpowiedź 3 nie może być puste")
    private String bad_answer3;
    @NotEmpty(message = "Pytanie nie może być puste")
    private String imageName;
}