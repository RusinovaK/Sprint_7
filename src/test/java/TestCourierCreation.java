import couriercreation.data.DataRequired;
import couriercreation.data.DataWithoutLogin;
import couriercreation.data.DataWithoutPassword;
import couriercreation.data.ValidData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCourierCreation {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Create courier with valid data and check status code and response")
    @Description("Check status code and response of /api/v1/courier (courier creation method) if send request with valid data in body")
    public void createCourierWithValidDataAndCheckStatusCodeAndResponse(){
        Response response = sendPostRequestWithValidData();
        checkStatusCodeAndResponseValidData(response);
        TestCourierLogin testCourierLogin = new TestCourierLogin();
        Response secResponse = testCourierLogin.sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Create courier with data required and check status code and response")
    @Description("Check status code and response of /api/v1/courier (courier creation method) if send request with required data in body")
    public void createCourierWithDataRequiredAndCheckStatusCodeAndResponse(){
        Response response = sendPostRequestWithDataRequired();
        checkStatusCodeAndResponseValidData(response);
        TestCourierLogin testCourierLogin = new TestCourierLogin();
        Response secResponse = testCourierLogin.sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Create courier with exists login and check status code and response")
    @Description("Check status code and response of /api/v1/courier (courier creation method) if send request with exists login in body")
    public void createCourierWithExistsLoginAndCheckStatusCodeAndResponse(){
        sendPostRequestWithValidData();
        Response response = sendPostRequestWithValidData();
        checkStatusCodeAndResponseExistsLogin(response);
        TestCourierLogin testCourierLogin = new TestCourierLogin();
        Response secResponse = testCourierLogin.sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Create courier with data without login field and check status code and response")
    @Description("Check status code and response of /api/v1/courier (courier creation method) if send request with data without login field in body")
    public void createCourierWithDataWithoutLoginFieldAndCheckStatusCodeAndResponse(){
        Response response = sendPostRequestWithDataWithoutLoginField();
        checkStatusCodeAndResponseWithoutRequiredField(response);
    }

    @Test
    @DisplayName("Create courier with data without password field and check status code and response")
    @Description("Check status code and response of /api/v1/courier (courier creation method) if send request with data without password field and without firstName field in body")
    public void createCourierWithDataWithoutPasswordFieldAndCheckStatusCodeAndResponse(){
        Response response = sendPostRequestWithDataWithoutPasswordField();
        checkStatusCodeAndResponseWithoutRequiredField(response);
    }

    @Step("Send POST request to /api/v1/courier with valid data")
    public Response sendPostRequestWithValidData(){
        ValidData data = new ValidData("ksenka916", "619aknesk", "ksenka916");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }
    @Step("Send POST request to /api/v1/courier with data required")
    public Response sendPostRequestWithDataRequired(){
        DataRequired data = new DataRequired("ksenka916", "619aknesk");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }
    @Step("Send POST request to /api/v1/courier with data without login field")
    public Response sendPostRequestWithDataWithoutLoginField(){
        DataWithoutLogin data = new DataWithoutLogin("619aknesk", "ksenka916");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }
    @Step("Send POST request to /api/v1/courier with data without password field")
    public Response sendPostRequestWithDataWithoutPasswordField(){
        DataWithoutPassword data = new DataWithoutPassword("ksenka916");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }
    @Step("Check status code and response. Method /api/v1/courier. Request send with valid data in body.")
    public void checkStatusCodeAndResponseValidData(Response response){
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
    @Step("Check status code and response. Method /api/v1/courier. Request send with exists login in body.")
    public void checkStatusCodeAndResponseExistsLogin(Response response){
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
    @Step("Check status code and response. Method /api/v1/courier. Request send with data without login/password field in body.")
    public void checkStatusCodeAndResponseWithoutRequiredField(Response response){
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
