package odine.freelancermarketplace.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.projection.JobDetailsProj;
import odine.freelancermarketplace.dto.web.JobCreationRequest;
import odine.freelancermarketplace.dto.web.JobUpdateRequest;
import odine.freelancermarketplace.model.Comment;
import odine.freelancermarketplace.model.Job;
import odine.freelancermarketplace.service.CommentService;
import odine.freelancermarketplace.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Jobs")
public class JobsController {
    private final JobService jobService;
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Job createJob(@RequestBody JobCreationRequest request) {
        return jobService.createJob(request);
    }

    @GetMapping("/{id}")
    public JobDetailsProj getJobDetails(@PathVariable Long id) {
        return jobService.getJobDetailsById(id);
    }

    @PatchMapping("/{id}")
    public Job updateJob(@RequestBody JobUpdateRequest request, @PathVariable Long id) {
        return jobService.updateJob(id, request);
    }

    @GetMapping("/{id}/comments")
    public Set<Comment> getCommentsForJob(@PathVariable Long id) {
        return commentService.getCommentsForJob(id);
    }
}
