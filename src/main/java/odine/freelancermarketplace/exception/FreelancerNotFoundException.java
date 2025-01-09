package odine.freelancermarketplace.exception;

public class FreelancerNotFoundException extends RuntimeException {
    public FreelancerNotFoundException(String id) {
        super("Freelancer with identifier: " + id + " not found");
    }
}
