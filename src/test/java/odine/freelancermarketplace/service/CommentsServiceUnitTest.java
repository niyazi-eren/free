package odine.freelancermarketplace.service;

import odine.freelancermarketplace.dto.web.CommentCreationRequest;
import odine.freelancermarketplace.dto.web.CommentUpdateRequest;
import odine.freelancermarketplace.model.Comment;
import odine.freelancermarketplace.model.Job;
import odine.freelancermarketplace.repository.CommentRepository;
import odine.freelancermarketplace.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceUnitTest {
    @InjectMocks
    private CommentService commentsService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    void shouldCreateComment() {
        // Given
        CommentCreationRequest request = new CommentCreationRequest(1L, "John Doe", "Great job!");
        Job job = new Job();
        job.setId(1L);

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        var expectedComment = new Comment();
        expectedComment.setCommenterName(request.commenterName());
        expectedComment.setContent(request.content());
        expectedComment.setJob(job);

        when(commentRepository.save(any(Comment.class))).thenReturn(expectedComment);

        // When
        Comment result = commentsService.createComment(request);

        // Then
        assertThat(result.getCommenterName()).isEqualTo("John Doe");
        assertThat(result.getContent()).isEqualTo("Great job!");
        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateComment() {
        //  Given
        var request = new CommentUpdateRequest("content");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setCommenterName("John Doe");
        comment.setContent("old content");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When
        var result = commentsService.updateComment(1L, request);

        // Then
        assertThat(result.getCommenterName()).isEqualTo("John Doe");
        assertThat(result.getContent()).isEqualTo(request.content());
    }

}
