package odine.freelancermarketplace.web;

import odine.freelancermarketplace.exception.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentsControllerAdvice {
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ExNotFoundHandler(CommentNotFoundException ex) {
        return ex.getMessage();
    }
}
