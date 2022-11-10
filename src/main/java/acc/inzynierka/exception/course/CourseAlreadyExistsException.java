package acc.inzynierka.exception.course;

import acc.inzynierka.exception.ApiRuntimeException;


public class CourseAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public CourseAlreadyExistsException() {
        super(String.format("Nazwa kursu jest już w użyciu"));
    }

}
