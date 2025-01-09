package odine.freelancermarketplace;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = FreelancerMarketplaceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractDbIntTest {

    // will be shared between test methods
    @Container
    public static PostgreSQLContainer<?> POSTGRES_CONTAINER = PostgresTestContainer.getInstance();
}