package acc.inzynierka.modelsDTO.mobileapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link acc.inzynierka.models.Exercise} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExerciseMobileDto implements Serializable {
    private String question;
    private String answer;
    private List<String> options;
    private String imageUrl;
}