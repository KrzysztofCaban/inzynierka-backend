package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.TestQuestionDto;
import lombok.Data;

@Data
public class TestQuestionResponse {
    private TestQuestionDto testQuestion;
    private String message;
}
