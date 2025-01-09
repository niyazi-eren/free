package odine.freelancermarketplace;


import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    public static final String IMAGE_NAME = "postgres:17.2-alpine";
    public static final String INIT_DB_SCRIPT = "schema-test.sql";
    private static PostgresTestContainer POSTGRES_CONTAINER;

    private PostgresTestContainer() {
        super(IMAGE_NAME);
    }

    public static PostgresTestContainer getInstance() {
        if (POSTGRES_CONTAINER == null) {
            POSTGRES_CONTAINER = new PostgresTestContainer()
                    .withInitScript(INIT_DB_SCRIPT);
        }
        return POSTGRES_CONTAINER;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", POSTGRES_CONTAINER.getJdbcUrl());
        System.setProperty("DB_USERNAME", POSTGRES_CONTAINER.getUsername());
        System.setProperty("DB_PASSWORD", POSTGRES_CONTAINER.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
