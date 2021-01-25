import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FirstAPITests {

    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void assertThatIsAbbleToGetUserList() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .log().body()
                .body(is(notNullValue()));
    }

    @Test
    void assertThatTotalPagesAmountEqualsTwo() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .log().body()
                .body("total_pages", is(2));
    }

    @Test
    void assertThatItIsPossibleToCreateUser() {
        String data = "{\n" +
                "    \"name\": \"Sergei\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

                given()
                        .contentType(ContentType.JSON)
                        .body(data)
                        .when()
                        .post("/users")
                        .then()
                        .statusCode(201)
                        .log().body()
                        .body("name", is("Sergei"));
    }

    @Test
    void assertThatOnlySixUsersDisplayedOnThePage() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.id", is(not(7)));
    }

    @Test
    void assertThatItIsPossibleToRegisterNewUser() {
        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .log().body();
    }


}
