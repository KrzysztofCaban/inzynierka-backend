package acc.inzynierka.exception;

public class CategoryNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException(String categoryName, String message) {
        super(String.format("Błąd dla [%s]: %s", categoryName, message));
    }
}
