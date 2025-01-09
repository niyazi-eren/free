package odine.freelancermarketplace.service;

import odine.freelancermarketplace.dto.web.FreelancerCreationRequest;
import odine.freelancermarketplace.enums.FreelancerType;
import odine.freelancermarketplace.messaging.EvaluationRequestProducer;
import odine.freelancermarketplace.repository.DesignToolRepository;
import odine.freelancermarketplace.repository.FreelancerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FreelancersServiceUnitTest {
    @InjectMocks
    private FreelancerService freelancersService;

    @Mock
    private FreelancerRepository freelancerRepository;

    @Mock
    private EvaluationRequestProducer evaluationRequestProducer;

    @Mock
    private DesignToolRepository designToolRepository;

    @Test
    public void testCreateFreelancerSendsRequestToQueue() {
        // Given
        var request = new FreelancerCreationRequest(
                "John Doe",
                "john.doe@example.com",
                "+123456789",
                "New York",
                FreelancerType.DESIGNER,
                "https://portfolio.johndoe.com",
                List.of("Photoshop", "Figma"),
                List.of("Java", "Python"),
                List.of("Backend Development", "API Design"));

        when(freelancerRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        // When
        freelancersService.createFreelancer(request);

        // Then
        verify(evaluationRequestProducer, times(1)).send(request);
    }
}