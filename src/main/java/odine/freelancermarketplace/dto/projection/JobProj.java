package odine.freelancermarketplace.dto.projection;

import odine.freelancermarketplace.enums.JobStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface JobProj {
    Long getId();

    LocalDateTime getCreatedAt();

    String getDescription();

    JobStatus getJobStatus();

    Set<CommentProj> getComments();
}
