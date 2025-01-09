package odine.freelancermarketplace.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentCreationRequest(
        @JsonProperty("commenter_name") String commenterName,
        String content
) {
}
