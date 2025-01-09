package odine.freelancermarketplace.repository;

import odine.freelancermarketplace.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
    SELECT c
    FROM Comment c
    WHERE c.job.id = :jobId
    """)
    Set<Comment> findAllForJob(@Param("jobId") Long jobId);
}
