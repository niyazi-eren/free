package odine.freelancermarketplace.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import odine.freelancermarketplace.AbstractDbIntTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CommentsIntegrationTest extends AbstractDbIntTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCreateCommentForJob() {
        String requestBody = """
                {
                  "commenter_name": "Jone",
                  "content": "I'd like to apply!"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/jobs/1/comments")
                .then()
                .statusCode(201)
                .body("commenterName", equalTo("Jone"))
                .body("content", equalTo("I'd like to apply!"));
    }

    @Test
    void shouldUpdateComment() {
        String content = "Updated content";

        String requestBody = """
                { "content": "%s" }
                """.formatted(content);

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .patch("/api/comments/2")
                .then()
                .statusCode(200)
                .body("commenterName", equalTo("commenter2"))
                .body("content", equalTo(content));
    }

    @Test
    void shouldNotFindComment() {
        String content = "Updated content";

        String requestBody = """
                { "content": "%s" }
                """.formatted(content);

        String wrongId = "5";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .patch("/api/comments/" + wrongId)
                .then()
                .statusCode(404);
    }
}
