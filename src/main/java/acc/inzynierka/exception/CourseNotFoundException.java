package acc.inzynierka.exception;

public class CourseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CourseNotFoundException(Long id, String message) {
        super(String.format("Błąd dla id [%d]: %s", id, message));
    }

    public CourseNotFoundException(String courseName, String message) {
        super(String.format("Błąd dla [%s]: %s", courseName, message));
    }
}
