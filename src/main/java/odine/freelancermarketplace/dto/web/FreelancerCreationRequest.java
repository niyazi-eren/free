package odine.freelancermarketplace.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import odine.freelancermarketplace.enums.FreelancerType;

import java.util.List;

public record FreelancerCreationRequest(
        String name,
        String email,
        String phone,
        String city,
        @JsonProperty("freelancer_type") FreelancerType freelancerType,
        @JsonProperty("portfolio_url") String portfolioUrl,
        @JsonProperty("design_tools") List<String> designTools,
        List<String> languages,
        List<String> specialties) {
}