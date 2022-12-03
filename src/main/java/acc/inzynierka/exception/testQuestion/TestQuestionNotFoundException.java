package acc.inzynierka.exception.testQuestion;

import acc.inzynierka.exception.ApiRuntimeException;

public class TestQuestionNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public TestQuestionNotFoundException() {
        super("Nie znaleziono pytania testowego");
    }
}
