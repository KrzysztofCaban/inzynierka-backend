package acc.inzynierka.exception.potentialCategory;

import acc.inzynierka.exception.ApiRuntimeException;

public class PotentialCategoryAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public PotentialCategoryAlreadyExistsException() {
        super("Podana nazwa kategorii jest już oczekuje na akceptację");
    }
}
