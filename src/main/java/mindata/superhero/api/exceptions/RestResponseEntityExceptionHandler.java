package mindata.superhero.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HeroNotFoundException.class)
    public ResponseEntity<Object> handleHeroNotFound(RuntimeException ex, WebRequest request) {
        String validationMessage = ex.getMessage();
        return handleExceptionInternal(ex, validationMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidHeroException.class)
    public ResponseEntity<Object> handleInvalidHero(RuntimeException ex, WebRequest request) {
        String validationMessage = ex.getMessage();
        return handleExceptionInternal(ex, validationMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
