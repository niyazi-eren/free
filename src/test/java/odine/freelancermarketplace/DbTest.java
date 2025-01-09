package odine.freelancermarketplace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DbTest extends AbstractDbIntTest {
    @Test
    void testDatabaseConnection() {
        Assertions.assertTrue(POSTGRES_CONTAINER.isRunning());
    }
}