package odine.freelancermarketplace.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import odine.freelancermarketplace.AbstractDbIntTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JobsIntegrationTest extends AbstractDbIntTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateJob() {
        String description = "Test description";
        String requestBody = """
                {
                  "freelancer_id": 1,
                  "description": "%s"
                }
                """.formatted(description);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/jobs")
                .then()
                .statusCode(201)
                .body("jobStatus", equalTo("AVAILABLE"))
                .body("description", equalTo(description));
    }

    @Test
    void shouldGetJobById() {
        given()
                .when()
                .get("/api/jobs/1")
                .then()
                .statusCode(200)
                .body("jobStatus", equalTo("AVAILABLE"))
                .body("description", equalTo("Create website"));
    }

    @Test
    void shouldNotGetJobById() {
        given()
                .when()
                .get("/api/jobs/99")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldUpdateJob() {
        String description = "Updated description";

        String requestBody = """
                { "description": "%s" }
                """.formatted(description);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .patch("/api/jobs/1")
                .then()
                .statusCode(200)
                .body("jobStatus", equalTo("AVAILABLE"))
                .body("description", equalTo(description));
    }

    @Test
    void shouldGetCommentsForJob() {
        given()
                .when()
                .get("/api/jobs/1/comments")
                .then()
                .statusCode(200)
                .body("[0].content", equalTo("nice job"))
                .body("[0].commenterName", equalTo("commenter"));
    }

    @Test
    void shouldNotGetAnyCommentsForJob() {
        given()
                .when()
                .get("/api/jobs/2/comments")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }
}
