package acc.inzynierka.modelsDTO.mobileapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link acc.inzynierka.models.TestQuestion} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TestQuestionMobileDto implements Serializable {
    private String question;
    private String answer;
    private String imageUrl;
}