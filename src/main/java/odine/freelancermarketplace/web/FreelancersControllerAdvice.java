package odine.freelancermarketplace.web;

import odine.freelancermarketplace.exception.FreelancerAlreadyExistsException;
import odine.freelancermarketplace.exception.FreelancerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FreelancersControllerAdvice {
    @ExceptionHandler(FreelancerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String freelancerAlreadyExistsHandler(FreelancerAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(FreelancerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String freelancerNotFoundHandler(FreelancerNotFoundException ex) { return ex.getMessage(); }
}
