package odine.freelancermarketplace.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentCreationRequest(
        @JsonProperty("job_id") Long jobId,
        @JsonProperty("commenter_name") String commenterName,
        String content
) {
}
