package odine.freelancermarketplace.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import odine.freelancermarketplace.AbstractDbIntTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FreelancersIntegrationTest extends AbstractDbIntTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldGetAllFreelancers() {
        given()
                .when()
                .get("/api/freelancers")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("John Doe"))
                .body("[1].specialties.find { it.name == 'Backend' }", notNullValue());
    }

    @Test
    void shouldGetSpecificFreelancerByEmail() {
        given()
        .when()
                .get("/api/freelancers/johndoe@example.com")
                .then()
                .statusCode(200)
                .body("name", equalTo("John Doe"))
                .body("designTools.find { it.name == 'Figma' }", notNullValue())
                .body("designTools.find { it.name == 'Adobe' }", notNullValue());
    }

    @Test
    void shouldNotFindFreelancerByEmail() {
        given()
                .when()
                .get("/api/freelancers/fake@example.com")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldFindDeveloperByLanguagesAndCity() {
        String city = "francisco";
        String language = "Java";
        String params = "?city=" + city + "&language=" + language;

        given()
                .when()
                .get("/api/freelancers/search" + params)
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].name", equalTo("Jane Smith"))
                .body("[0].languages.find { it.name == 'Java' }", notNullValue())
                .body("[0].languages.find { it.name == 'Go' }", notNullValue());
    }

    @Test
    void shouldFindDesignerByFreelancerTypeAndNameCaseInsensitive() {
        String freelancerType = "DESIGNER";
        String name = "john";
        String params = "?freelancerType=" + freelancerType + "&name=" + name;

        given()
        .when()
                .get("/api/freelancers/search" + params)
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].name", equalTo("John Doe"));
    }

    @Test
    void shouldCreateNewFreelancer() {
        String requestBody = """
                {
                  "name": "dev",
                  "email": "new@example.com",
                  "phone": "+0987654321",
                  "city": "San Francisco",
                  "freelancer_type": "SOFTWARE_DEVELOPER",
                  "languages": ["Java", "Go", "Python"],
                  "specialties": ["Frontend", "Backend"]
                }""";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/freelancers")
                .then()
                .statusCode(201)
                .body("name", equalTo("dev"))
                .body("freelancerType", equalTo("SOFTWARE_DEVELOPER"))
                .body("email", equalTo("new@example.com"));
    }

    @Test
    void shouldNotCreateFreelancerIfAlreadyExists() {
        String requestBody = """
                 {
                   "name": "Jane Smith",
                   "email": "janesmith@example.com",
                   "phone": "+0987654321",
                   "city": "San Francisco",
                   "freelancer_type": "SOFTWARE_DEVELOPER",
                   "languages": ["Java", "Go", "Python"],
                   "specialties": ["Frontend", "Backend"]
                 }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/api/freelancers")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void shouldGetJobsForFreelancer() {
        given()
                .when()
                .get("/api/freelancers/2/jobs")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].jobStatus", equalTo("AVAILABLE"))
                .body("[1].jobStatus", equalTo("FINISHED"));

    }
}
