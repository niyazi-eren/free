package odine.freelancermarketplace.dto.projection;

import odine.freelancermarketplace.enums.JobStatus;

import java.time.LocalDateTime;

public interface JobDetailsProj {
    Long getId();

    LocalDateTime getCreatedAt();

    String getDescription();

    JobStatus getJobStatus();

    Long getFreelancerId();
}
