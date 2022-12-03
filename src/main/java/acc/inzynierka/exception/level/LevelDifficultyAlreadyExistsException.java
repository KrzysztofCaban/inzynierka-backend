package acc.inzynierka.exception.level;

import acc.inzynierka.exception.ApiRuntimeException;

public class LevelDifficultyAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public LevelDifficultyAlreadyExistsException() {
        super("Poziom o podanym poziomie trudności jest już w użyciu w obecnym kursie");
    }
}
