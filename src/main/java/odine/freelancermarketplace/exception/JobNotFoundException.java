package odine.freelancermarketplace.exception;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException(String id) {
      super("Job with id : " + id + " not found");
  }
}
