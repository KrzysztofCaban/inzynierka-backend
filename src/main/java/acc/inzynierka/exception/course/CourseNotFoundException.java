package acc.inzynierka.exception.course;

import acc.inzynierka.exception.ApiRuntimeException;

public class CourseNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public CourseNotFoundException() {
        super("Nie znaleziono kursu");
    }
}
