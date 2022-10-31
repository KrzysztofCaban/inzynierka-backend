package acc.inzynierka.exception;

public class ApiRuntimeException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ApiRuntimeException(String message) {
        super(message);
    }
}
