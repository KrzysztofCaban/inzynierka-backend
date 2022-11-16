package acc.inzynierka.exception.image;

import acc.inzynierka.exception.ApiRuntimeException;

public class ImageAlreadyExistsException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public ImageAlreadyExistsException() {
        super("Zdjęcie o tej nazwie jest już w użyciu");
    }
}
