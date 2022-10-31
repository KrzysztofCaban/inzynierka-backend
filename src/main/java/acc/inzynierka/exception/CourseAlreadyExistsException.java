package acc.inzynierka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CourseAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CourseAlreadyExistsException(String categoryName, String message) {
        super(String.format("Błąd dla nazwy [%s]: %s", categoryName, message));
    }

    public CourseAlreadyExistsException() {
    }
}
