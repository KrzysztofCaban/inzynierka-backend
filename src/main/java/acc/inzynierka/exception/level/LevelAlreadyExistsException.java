package acc.inzynierka.exception.level;

import acc.inzynierka.exception.ApiRuntimeException;

public class LevelAlreadyExistsException extends ApiRuntimeException {

    private static final long serialVersionUID = 1L;

    public LevelAlreadyExistsException() {
        super("Nazwa poziomu jest już w użyciu");
    }
}
