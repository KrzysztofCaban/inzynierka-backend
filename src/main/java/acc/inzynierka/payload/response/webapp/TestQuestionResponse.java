package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.TestQuestionDto;
import lombok.Data;

@Data
public class TestQuestionResponse {
    private TestQuestionDto testQuestion;
    private String message;
}
