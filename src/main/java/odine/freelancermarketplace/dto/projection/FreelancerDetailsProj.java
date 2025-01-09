package odine.freelancermarketplace.dto.projection;

import odine.freelancermarketplace.enums.FreelancerType;

import java.util.Set;

public interface FreelancerDetailsProj {
    Long getId();

    String getName();

    String getEmail();

    String getPhone();

    String getCity();

    Integer getEvaluationScore();

    FreelancerType getFreelancerType();

    Set<JobProj> getJobs();
}
