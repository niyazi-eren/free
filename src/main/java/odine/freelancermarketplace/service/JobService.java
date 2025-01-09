package odine.freelancermarketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.projection.JobDetailsProj;
import odine.freelancermarketplace.dto.projection.JobProj;
import odine.freelancermarketplace.dto.web.JobCreationRequest;
import odine.freelancermarketplace.dto.web.JobUpdateRequest;
import odine.freelancermarketplace.enums.JobStatus;
import odine.freelancermarketplace.exception.FreelancerNotFoundException;
import odine.freelancermarketplace.exception.JobNotFoundException;
import odine.freelancermarketplace.model.Job;
import odine.freelancermarketplace.repository.FreelancerRepository;
import odine.freelancermarketplace.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final FreelancerRepository freelancerRepository;

    public Job createJob(JobCreationRequest request) {
        Job job = new Job();

        job.setJobStatus(request.jobStatus() != null ? request.jobStatus() : JobStatus.AVAILABLE);

        job.setDescription(request.description());

        var freelancer = freelancerRepository.findById(request.freelancerId())
                .orElseThrow(() -> {
                    log.error("Freelancer with id {} not found", request.freelancerId());
                    return new FreelancerNotFoundException(request.freelancerId().toString());
                });

        job.setFreelancer(freelancer);

        return jobRepository.save(job);
    }

    public List<JobProj> getJobsForFreelancerById(Long id) {
        return jobRepository.getJobsForFreelancerById(id);
    }

    public JobDetailsProj getJobDetailsById(Long id) {
        return jobRepository.getJobDetailsById(id).orElseThrow(() -> {
            log.error("Job with id {} not found", id);
            return new JobNotFoundException(id.toString());
        });
    }

    public Job updateJob(Long id, JobUpdateRequest request) {
        Job job = jobRepository.findById(id).orElseThrow(() -> {
            log.error("Job with id {} not found for update", id);
            return new JobNotFoundException(id.toString());
        });

        if (request.description() != null && !request.description().isEmpty()) {
            job.setDescription(request.description());
        }
        if (request.jobStatus() != null) {
            job.setJobStatus(request.jobStatus());
        }

        return jobRepository.save(job);
    }
}
