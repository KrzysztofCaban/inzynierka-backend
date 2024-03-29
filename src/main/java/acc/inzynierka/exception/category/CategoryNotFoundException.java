package acc.inzynierka.exception.category;

import acc.inzynierka.exception.ApiRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException() {
        super("Nie znaleziono podanej kategorii w bazie");
    }
}
