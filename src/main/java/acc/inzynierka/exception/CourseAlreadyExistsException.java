package acc.inzynierka.exception;

public class CourseAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CourseAlreadyExistsException(String categoryName, String message) {
        super(String.format("Błąd dla nazwy [%s]: %s", categoryName, message));
    }
}
