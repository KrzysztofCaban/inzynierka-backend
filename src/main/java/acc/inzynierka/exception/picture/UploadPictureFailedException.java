package acc.inzynierka.exception.picture;

import acc.inzynierka.exception.ApiRuntimeException;

public class UploadPictureFailedException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UploadPictureFailedException() {
        super(String.format("Nie znaleziono podanego statusu w bazie"));
    }
}
