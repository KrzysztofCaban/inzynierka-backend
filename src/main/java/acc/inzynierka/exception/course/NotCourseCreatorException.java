package acc.inzynierka.exception.course;

import acc.inzynierka.exception.ApiRuntimeException;

public class NotCourseCreatorException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public NotCourseCreatorException() {
        super("Zalogowany użytkownik nie jest autorem kursu");
    }
}