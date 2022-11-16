package acc.inzynierka.exception.exercise;

import acc.inzynierka.exception.ApiRuntimeException;

public class ExerciseAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public ExerciseAlreadyExistsException() {
        super("Podana wyrażenie ćwiczenia jest już w użyciu");
    }
}
