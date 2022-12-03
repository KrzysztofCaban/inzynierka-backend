package acc.inzynierka.exception.usercourse;

import acc.inzynierka.exception.ApiRuntimeException;

public class UserIsNotInCourseException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UserIsNotInCourseException() {
        super("Nie jesteś zapisany do kursu");
    }
}
