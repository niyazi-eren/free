package odine.freelancermarketplace.web;

import odine.freelancermarketplace.exception.JobNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JobsControllerAdvice {

    @ExceptionHandler(JobNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String JobNotFoundHandler(JobNotFoundException ex) {
        return ex.getMessage();
    }
}
