package acc.inzynierka.exception.potentialCategory;

import acc.inzynierka.exception.ApiRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PotentialCategoryNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public PotentialCategoryNotFoundException() {
        super(String.format("Nie znaleziono podanej prośby o dodanie nowej kategorii"));
    }
}
