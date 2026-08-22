package exceptions;

public class DogException extends Exception {
    public DogException() {
        super("DogException");
    }

    public DogException(String message) {
        super(message);
    }
}
