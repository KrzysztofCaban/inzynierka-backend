package acc.inzynierka.exception.exercise;

import acc.inzynierka.exception.ApiRuntimeException;

public class ExerciseNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public ExerciseNotFoundException() {
        super(String.format("Nie znaleziono ćwiczenia"));
    }
}
