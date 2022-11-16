package acc.inzynierka.exception.testQuestion;

import acc.inzynierka.exception.ApiRuntimeException;

public class TestQuestionAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public TestQuestionAlreadyExistsException() {
        super("Podane odpiedź testu jest już w użyciu");
    }
}
