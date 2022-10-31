package acc.inzynierka.exception.course;

import acc.inzynierka.exception.ApiRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CourseNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public CourseNotFoundException() {
        super(String.format("Nie znaleziono kursu"));
    }
}
