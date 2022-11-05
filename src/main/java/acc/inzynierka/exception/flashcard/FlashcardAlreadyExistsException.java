package acc.inzynierka.exception.flashcard;

import acc.inzynierka.exception.ApiRuntimeException;

public class FlashcardAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public FlashcardAlreadyExistsException() {
        super(String.format("Podane wyrażenie fiszki jest już w użyciu"));
    }
}
