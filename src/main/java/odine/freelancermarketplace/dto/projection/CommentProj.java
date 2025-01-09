package odine.freelancermarketplace.dto.projection;


import java.time.LocalDateTime;

public interface CommentProj {

    Long getId();

    String getContent();

    String getCommenterName();

    LocalDateTime getCreatedAt();
}
