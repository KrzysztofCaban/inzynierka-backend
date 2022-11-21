package acc.inzynierka.exception.user;

import acc.inzynierka.exception.ApiRuntimeException;

public class PasswordNotCorrectException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public PasswordNotCorrectException() {
        super("Podane błędne hasło");
    }
}
