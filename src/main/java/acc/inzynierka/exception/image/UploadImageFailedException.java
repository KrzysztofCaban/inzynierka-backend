package acc.inzynierka.exception.image;

import acc.inzynierka.exception.ApiRuntimeException;

public class UploadImageFailedException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UploadImageFailedException() {
        super("Zapisanie zdjęcia nie powiodło się");
    }
}
