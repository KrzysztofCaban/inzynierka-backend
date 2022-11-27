package acc.inzynierka.exception.usercourse;

import acc.inzynierka.exception.ApiRuntimeException;

public class UserIsInCourseAlreadyException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UserIsInCourseAlreadyException() {
        super("Jesteś już zapisany do wybranego kursu");
    }
}
