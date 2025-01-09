package odine.freelancermarketplace.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import odine.freelancermarketplace.dto.web.CommentCreationRequest;
import odine.freelancermarketplace.dto.web.CommentUpdateRequest;
import odine.freelancermarketplace.model.Comment;
import odine.freelancermarketplace.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "Comments")
public class CommentsController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment createComment(@RequestBody CommentCreationRequest request) {
        return commentService.createComment(request);
    }

    @PatchMapping("/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody CommentUpdateRequest request) {
        return commentService.updateComment(id, request);
    }
}
