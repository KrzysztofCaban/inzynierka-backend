package acc.inzynierka.exception.status;

import acc.inzynierka.exception.ApiRuntimeException;

public class StatusNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public StatusNotFoundException() {
        super(String.format("Nie znaleziono podanego statusu w bazie"));
    }
}
