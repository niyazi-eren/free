package odine.freelancermarketplace.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import odine.freelancermarketplace.enums.JobStatus;

public record JobCreationRequest(
        @JsonProperty("freelancer_id") Long freelancerId,
        @JsonProperty("job_status") JobStatus jobStatus,
        String description
) {
}
