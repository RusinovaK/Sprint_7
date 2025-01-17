import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestDeleteCourier {
    @Step("Send DELETE request to /api/v1/courier/ with valid queryParam and body.")
    public void sendDeleteRequestWithValidQueryParamAndBody(Response response){
        TestCourierLogin testCourierLogin = new TestCourierLogin();
        given()
                .header("Content-type", "application/json")
                .and()
                .body(testCourierLogin.deserializeDataFromResponse(response))
                .when()
                .delete("/api/v1/courier/" + testCourierLogin.serializeDataFromResponseIntoString(testCourierLogin.deserializeDataFromResponse(response)));
    }
}
