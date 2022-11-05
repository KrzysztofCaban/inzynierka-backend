package acc.inzynierka.exception.flashcard;

import acc.inzynierka.exception.ApiRuntimeException;

public class FlashcardNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public FlashcardNotFoundException() {
        super(String.format("Nie znaleziono fiszki"));
    }
}
