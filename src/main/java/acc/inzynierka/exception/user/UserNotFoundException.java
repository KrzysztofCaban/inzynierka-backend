package acc.inzynierka.exception.user;

import acc.inzynierka.exception.ApiRuntimeException;

public class UserNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super(String.format("Nie znaleziono użytkownika w bazie"));
    }
}
