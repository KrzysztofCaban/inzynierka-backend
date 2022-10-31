package acc.inzynierka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException() {
        super(String.format("Nie znaleziono podanej kategorii w bazie"));
    }
}
