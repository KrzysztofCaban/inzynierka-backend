package acc.inzynierka.exception.category;

import acc.inzynierka.exception.ApiRuntimeException;

public class CategoryIsUsedException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public CategoryIsUsedException() {
        super("Podana kategoria jest w użyciu");
    }
}
