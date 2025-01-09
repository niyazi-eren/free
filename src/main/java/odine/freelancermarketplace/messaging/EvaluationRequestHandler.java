package odine.freelancermarketplace.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import odine.freelancermarketplace.dto.web.FreelancerCreationRequest;
import odine.freelancermarketplace.repository.FreelancerRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static odine.freelancermarketplace.messaging.RabbitMQConfiguration.SCORE_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class EvaluationRequestHandler {
    private final FreelancerRepository freelancerRepository;

    @RabbitListener(queues = SCORE_QUEUE)
    public void consumeMessage(FreelancerCreationRequest request) {
        log.info("Received request for: {}", request.email());

        int score = computeFreelancerScore(request);

        // delay 10s
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted", e);
            return;
        }

        freelancerRepository.updateEvaluationScore(request.email(), score);

        log.info("Updated evaluation score to: {} for: {}", score, request.email());
    }

    private int computeFreelancerScore(FreelancerCreationRequest request) {
        int score = switch (request.freelancerType()) {
            case DESIGNER -> Math.min(request.designTools().size(), 10);
            case SOFTWARE_DEVELOPER -> Math.min(request.specialties().size() * request.languages().size(), 10);
        };
        return Math.max(score, 1);
    }
}
