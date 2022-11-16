package acc.inzynierka.exception.image;

import acc.inzynierka.exception.ApiRuntimeException;

public class ImageNotFoundException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public ImageNotFoundException() {
        super("Nie znaleziono podanego zdjęcia w bazie");
    }
}
