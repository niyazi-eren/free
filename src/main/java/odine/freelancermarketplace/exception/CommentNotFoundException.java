package odine.freelancermarketplace.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String id) {

      super("Comment with id : " + id + " not found");
    }
}
