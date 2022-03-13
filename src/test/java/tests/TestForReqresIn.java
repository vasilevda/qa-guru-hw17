package tests;

import models.User;
import models.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class TestForReqresIn {

    @Test
    void testGetAllListUsers() {
        UserData userData = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().body().as(UserData.class);

        for (User user: userData.getData()) {
            Assertions.assertNotNull(user.getEmail());
        }
    }

    @Test
    void testGetListUsers() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total_pages", is(2))
                .body("data.email[1]", is("lindsay.ferguson@reqres.in"));
    }

    @Test
    void testGetSingleUsers() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"));
    }

    @Test
    void testGetSingleUsersNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetListResource() {
        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("data.name[0]", is("cerulean"))
                .body("data.name[1]", is("fuchsia rose"))
                .body("data.name[2]", is("true red"))
                .body("data.name[3]", is("aqua sky"))
                .body("data.name[4]", is("tigerlily"))
                .body("data.name[5]", is("blue turquoise"));
    }

    @Test
    void testGetSingleResourceNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);
    }
}
