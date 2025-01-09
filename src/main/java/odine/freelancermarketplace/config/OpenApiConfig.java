package odine.freelancermarketplace.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "local env"),
        }
)
public class OpenApiConfig {

}
