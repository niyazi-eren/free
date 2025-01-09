package odine.freelancermarketplace.repository;

import odine.freelancermarketplace.dto.projection.JobProj;
import odine.freelancermarketplace.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("""
                SELECT j
                FROM Job j
                WHERE j.freelancer.id = :freelancerId
            """)
    List<JobProj> getJobsForFreelancerById(@Param("freelancerId") Long freelancerId);

    @Query("""
            SELECT j
            FROM Job j
            WHERE j.id = :id
            """)
    Optional<JobProj> getJobDetailsById(@Param("id") Long id);
}