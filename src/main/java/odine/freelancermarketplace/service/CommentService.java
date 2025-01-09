package odine.freelancermarketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.web.CommentCreationRequest;
import odine.freelancermarketplace.dto.web.CommentUpdateRequest;
import odine.freelancermarketplace.exception.CommentNotFoundException;
import odine.freelancermarketplace.exception.JobNotFoundException;
import odine.freelancermarketplace.model.Comment;
import odine.freelancermarketplace.repository.CommentRepository;
import odine.freelancermarketplace.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JobRepository jobRepository;

    public Comment createComment(Long jobId, CommentCreationRequest request) {
        var comment = new Comment();
        comment.setCommenterName(request.commenterName());
        comment.setContent(request.content());

        var job = jobRepository.findById(jobId).orElseThrow(() -> {
            log.error("Comment creation failed, job with id: {} not found", jobId);
            return new JobNotFoundException(jobId.toString());
        });

        comment.setJob(job);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.error("Comment with id {} not found for update", id);
            return new CommentNotFoundException(id.toString());
        });
        comment.setContent(request.content());
        return commentRepository.save(comment);
    }

    public Set<Comment> getCommentsForJob(Long jobId) {
        return commentRepository.findAllForJob(jobId);
    }
}
