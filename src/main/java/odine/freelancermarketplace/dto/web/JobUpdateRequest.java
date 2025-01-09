package odine.freelancermarketplace.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import odine.freelancermarketplace.enums.JobStatus;

public record JobUpdateRequest(
        @JsonProperty("job_status") JobStatus jobStatus,
        String description
) {
}
