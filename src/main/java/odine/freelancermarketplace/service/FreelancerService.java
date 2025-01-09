package odine.freelancermarketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.projection.FreelancerDetailsProj;
import odine.freelancermarketplace.dto.web.FreelancerCreationRequest;
import odine.freelancermarketplace.exception.FreelancerAlreadyExistsException;
import odine.freelancermarketplace.exception.FreelancerNotFoundException;
import odine.freelancermarketplace.messaging.EvaluationRequestProducer;
import odine.freelancermarketplace.model.*;
import odine.freelancermarketplace.repository.DesignToolRepository;
import odine.freelancermarketplace.repository.FreelancerRepository;
import odine.freelancermarketplace.repository.LanguageRepository;
import odine.freelancermarketplace.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final LanguageRepository languageRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DesignToolRepository designToolRepository;
    private final FreelancerRepository freelancerRepository;
    private final EvaluationRequestProducer evaluationRequestProducer;

    public Freelancer createFreelancer(FreelancerCreationRequest request) {
        // check if freelancer already exists by mail
        var freelancerOpt = freelancerRepository.findByEmail(request.email());
        if (freelancerOpt.isPresent()) {
            log.error("Error freelancer with email: {} already exists", request.email());
            throw new FreelancerAlreadyExistsException(request.email());
        }

        Freelancer freelancer =
                switch (request.freelancerType()) {
                    case DESIGNER -> {
                        var designer = new Designer();
                        setFreelancerAttributes(designer, request);

                        // designer attributes
                        designer.setPortfolioUrl(request.portfolioUrl());
                        setDesignTools(request.designTools(), designer);

                        yield freelancerRepository.save(designer);
                    }
                    case SOFTWARE_DEVELOPER -> {
                        var developer = new SoftwareDeveloper();
                        setFreelancerAttributes(developer, request);

                        // dev attributes
                        setLanguages(request.languages(), developer);
                        setSpecialties(request.specialties(), developer);

                        yield freelancerRepository.save(developer);
                    }
                };

        // async score eval
        evaluationRequestProducer.send(request);

        return freelancer;
    }

    public List<FreelancerDetailsProj> findAll() {
        return freelancerRepository.findAllFreelancers();
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
