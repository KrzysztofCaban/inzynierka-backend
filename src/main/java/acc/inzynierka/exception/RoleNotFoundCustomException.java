package acc.inzynierka.exception;

import java.io.Serial;

public class RoleNotFoundCustomException extends ApiRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RoleNotFoundCustomException() {
        super("Zalogowany użytkownik nie jest autorem kursu");
    }
}
