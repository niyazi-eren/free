package odine.freelancermarketplace.exception;

public class FreelancerAlreadyExistsException extends RuntimeException {
    public FreelancerAlreadyExistsException(String email) {
        super("Freelancer with email: " + email + " already exists");
    }
}
