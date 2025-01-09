package odine.freelancermarketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.web.FreelancerCreationRequest;
import odine.freelancermarketplace.exception.FreelancerAlreadyExistsException;
import odine.freelancermarketplace.exception.FreelancerNotFoundException;
import odine.freelancermarketplace.model.*;
import odine.freelancermarketplace.repository.DesignToolRepository;
import odine.freelancermarketplace.repository.FreelancerRepository;
import odine.freelancermarketplace.repository.LanguageRepository;
import odine.freelancermarketplace.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static odine.freelancermarketplace.enums.FreelancerType.DESIGNER;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final LanguageRepository languageRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DesignToolRepository designToolRepository;
    private final FreelancerRepository freelancerRepository;

    public Freelancer createFreelancer(FreelancerCreationRequest request) {
        // check if freelancer already exists by mail
        var freelancerOpt = freelancerRepository.findByEmail(request.email());
        if (freelancerOpt.isPresent()) {
            log.error("Error freelancer with email: {} already exists", request.email());
            throw new FreelancerAlreadyExistsException(request.email());
        }

        if (request.freelancerType() == DESIGNER) {
            var freelancer = new Designer();
            setFreelancerAttributes(freelancer, request);

            // designer attributes
            freelancer.setPortfolioUrl(request.portfolioUrl());
            setDesignTools(request.designTools(), freelancer);

            updateFreelancerScoreAsync(request);
            return freelancerRepository.save(freelancer);
        }


        var freelancer = new SoftwareDeveloper();
        setFreelancerAttributes(freelancer, request);

        // freelancer attributes
        setLanguages(request.languages(), freelancer);
        setSpecialties(request.specialties(), freelancer);

        updateFreelancerScoreAsync(request);
        return freelancerRepository.save(freelancer);
    }

    private void updateFreelancerScoreAsync(FreelancerCreationRequest request) {
        ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // 1. evaluate freelancer score
        CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(3000); // simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    return computeFreelancerScore(request);
                }, executor)
                .thenApplyAsync(score -> {
                    try {
                        // 2. get score and update freelancer score in db
                        return freelancerRepository.updateEvaluationScore(request.email(), score);
                    } catch (Exception e) {
                        log.error("Error updating freelancer score", e);
                        return 0;
                    }
                }, executor)
                // 3. get response from db and close service
                .whenComplete((numUpdatedEntities, exception) -> {
                    if (numUpdatedEntities != 1) {
                        log.error("Error updating freelancer score.");
                    }
                    if (exception != null) {
                        log.error("Async job failed.", exception);
                    }
                    executor.shutdown();
                });
    }

    public List<Freelancer> findAll() {
        return freelancerRepository.findAll();
    }

    public Freelancer findFreelancerByEmail(String email) {
        return freelancerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Freelancer with email: {} not found", email);
                    return new FreelancerNotFoundException(email);
                });
    }

    public List<Freelancer> search(String name,
                                   String city,
                                   List<String> specialties,
                                   String type,
                                   List<String> designTools) {
        return freelancerRepository.search(name, city, type, specialties, designTools);
    }

    private Integer computeFreelancerScore(FreelancerCreationRequest request) {
        int score = switch (request.freelancerType()) {
            case DESIGNER -> Math.min(request.designTools().size(), 10);
            case SOFTWARE_DEVELOPER -> Math.min(request.specialties().size() * request.languages().size(), 10);
        };
        return Math.max(score, 1);
    }

    private void setFreelancerAttributes(Freelancer freelancer, FreelancerCreationRequest request) {
        freelancer.setName(request.name());
        freelancer.setEmail(request.email());
        freelancer.setPhone(request.phone());
        freelancer.setCity(request.city());
        freelancer.setFreelancerType(request.freelancerType());
    }

    private void setLanguages(List<String> languages, SoftwareDeveloper developer) {
        languages.forEach(languageName ->
                languageRepository.findByName(languageName)
                        .ifPresentOrElse(
                                developer::addLanguage,
                                () -> {
                                    var newLanguage = new Language();
                                    newLanguage.setName(languageName);
                                    developer.addLanguage(newLanguage);
                                }));
    }

    private void setSpecialties(List<String> specialties, SoftwareDeveloper developer) {
        specialties.forEach(specialty -> specialtyRepository.findByName(specialty)
                .ifPresentOrElse(developer::addSpecialty, () -> {
                    var newSpecialty = new Specialty();
                    newSpecialty.setName(specialty);
                    developer.addSpecialty(newSpecialty);
                }));
    }

    private void setDesignTools(List<String> designTools, Designer designer) {
        designTools.forEach(tool -> designToolRepository.findByName(tool)
                .ifPresentOrElse(designer::addDesignTool, () -> {
                    var newDesignTool = new DesignTool();
                    newDesignTool.setName(tool);
                    designer.addDesignTool(newDesignTool);
                }));
    }
}
