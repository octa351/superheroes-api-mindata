package mindata.superhero.api.exceptions;

public class InvalidHeroException extends RuntimeException {

    public InvalidHeroException(String message) {
        super(message);
    }
}