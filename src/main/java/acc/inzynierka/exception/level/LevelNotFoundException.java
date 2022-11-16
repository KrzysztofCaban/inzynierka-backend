package acc.inzynierka.exception.level;

import acc.inzynierka.exception.ApiRuntimeException;

public class LevelNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public LevelNotFoundException() {
        super("Nie znaleziono poziomu");
    }
}
