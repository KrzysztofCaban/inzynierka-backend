package acc.inzynierka.exception.course;

import acc.inzynierka.exception.ApiRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotCourseCreatorException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public NotCourseCreatorException() {
        super("Zalogowany użytkownik nie jest autorem kursu");
    }
}