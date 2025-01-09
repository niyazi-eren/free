package odine.freelancermarketplace.messaging;

import lombok.RequiredArgsConstructor;
import odine.freelancermarketplace.dto.web.FreelancerCreationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static odine.freelancermarketplace.messaging.RabbitMQConfiguration.SCORE_QUEUE;

@RequiredArgsConstructor
@Component
public class EvaluationRequestProducer {
    private final RabbitTemplate rabbitTemplate;

    public void send(FreelancerCreationRequest request) {
        rabbitTemplate.convertAndSend(SCORE_QUEUE, request);
    }
}