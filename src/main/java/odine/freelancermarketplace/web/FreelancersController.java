package odine.freelancermarketplace.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.projection.FreelancerDetailsProj;
import odine.freelancermarketplace.dto.projection.JobProj;
import odine.freelancermarketplace.dto.web.FreelancerCreationRequest;
import odine.freelancermarketplace.model.Freelancer;
import odine.freelancermarketplace.service.FreelancerService;
import odine.freelancermarketplace.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freelancers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Freelancers")
public class FreelancersController {
    private final FreelancerService freelancerService;
    private final JobService jobService;

    @Operation(
            summary = "${api.freelancer.create-freelancer.description}",
            description = "${api.freelancer.create-freelancer.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Freelancer createFreelancer(@RequestBody FreelancerCreationRequest request) {
        return freelancerService.createFreelancer(request);
    }

    @GetMapping
    public List<FreelancerDetailsProj> getAllFreelancers() {
        return freelancerService.findAll();
    }

    @GetMapping("/{email}")
    public Freelancer getFreelancerByEmail(@PathVariable String email) {
        return freelancerService.findFreelancerByEmail(email);
    }

    @GetMapping("/{id}/jobs")
    public List<JobProj> getAllJobsByFreelancerId(@PathVariable Long id) {
        return jobService.getJobsForFreelancerById(id);
    }

    @GetMapping("/search")
    public List<Freelancer> searchFreelancers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "specialty", required = false) List<String> specialties,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "design_tools", required = false) List<String> designTools
    ) {
        log.debug("receive search request with params: {}, {}, {}, {}, {}", name, city, specialties, type, designTools);
        return freelancerService.search(name, city, specialties, type, designTools);
    }
}
