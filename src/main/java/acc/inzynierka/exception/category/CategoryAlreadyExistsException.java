package acc.inzynierka.exception.category;

import acc.inzynierka.exception.ApiRuntimeException;

public class CategoryAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public CategoryAlreadyExistsException() {
        super(String.format("Podana nazwa kategorii jest już w użyciu"));
    }
}
