import com.google.gson.Gson;
import courierlogin.data.DataFromResponseCourierLogin;
import courierlogin.data.DataWithoutLogin;
import courierlogin.data.DataWithoutPassword;
import courierlogin.data.DataLogAndPass;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestCourierLogin {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Courier login with valid data and check status code and response")
    @Description("Check status code and response of /api/v1/courier/login (courier login method) if send request with valid data in body")
    public void courierLoginWithValidDataAndCheckStatusCodeAndResponse(){
        TestCourierCreation testCourierCreation = new TestCourierCreation();
        testCourierCreation.sendPostRequestWithValidData();
        Response response = sendPostRequestWithValidData();
        checkStatusCodeAndResponseValidData(response);
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(response);
    }

    @Test
    @DisplayName("Courier login without login and check status code and response")
    @Description("Check status code and response of /api/v1/courier/login (courier login method) if send request without login in body.")
    public void courierLoginWithoutLoginAndCheckStatusCodeAndResponse(){
        TestCourierCreation testCourierCreation = new TestCourierCreation();
        testCourierCreation.sendPostRequestWithValidData();
        Response response = sendPostRequestWithoutLogin();
        checkStatusCodeAndResponseWithoutRequiredField(response);
        Response secResponse = sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Courier login without password and check status code and response")
    @Description("Check status code and response of /api/v1/courier/login (courier login method) if send request without password in body.")
    public void courierLoginWithoutPasswordAndCheckStatusCodeAndResponse(){
        TestCourierCreation testCourierCreation = new TestCourierCreation();
        testCourierCreation.sendPostRequestWithValidData();
        Response response = sendPostRequestWithoutPassword();
        checkStatusCodeAndResponseWithoutRequiredField(response);
        Response secResponse = sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Courier login with invalid login and check status code and response")
    @Description("Check status code and response of /api/v1/courier/login (courier login method) if send request with invalid login and valid password in body")
    public void courierLoginWithInvalidLoginAndCheckStatusCodeAndResponse(){
        TestCourierCreation testCourierCreation = new TestCourierCreation();
        testCourierCreation.sendPostRequestWithValidData();
        Response response = sendPostRequestWithInvalidLogin();
        checkStatusCodeAndResponseWithInvalidField(response);
        Response secResponse = sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Courier login with invalid password and check status code and response")
    @Description("Check status code and response of /api/v1/courier/login (courier login method) if send request with valid login and invalid password in body")
    public void courierLoginWithInvalidPasswordAndCheckStatusCodeAndResponse(){
        TestCourierCreation testCourierCreation = new TestCourierCreation();
        testCourierCreation.sendPostRequestWithValidData();
        Response response = sendPostRequestWithInvalidPassword();
        checkStatusCodeAndResponseWithInvalidField(response);
        Response secResponse = sendPostRequestWithValidData();
        TestDeleteCourier testDeleteCourier = new TestDeleteCourier();
        testDeleteCourier.sendDeleteRequestWithValidQueryParamAndBody(secResponse);
    }

    @Test
    @DisplayName("Courier login with data of non existent user and check status code and response")
    @Description("Check status code and response of /api/v1/courier/login (courier login method) if send request with login and password of non-existent user in body")
    public void courierLoginWithDataOfNonExistentUserAndCheckStatusCodeAndResponse(){
        Response response = sendPostRequestWithDataOfNonExistentUser();
        checkStatusCodeAndResponseWithInvalidField(response);
    }


    @Step("Send POST request to /api/v1/courier/login with valid data.")
    public Response sendPostRequestWithValidData(){
        DataLogAndPass data = new DataLogAndPass("ksenka916", "619aknesk");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login without login.")
    public Response sendPostRequestWithoutLogin(){
        DataWithoutLogin data = new DataWithoutLogin("619aknesk");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login without password.")
    public Response sendPostRequestWithoutPassword(){
        DataWithoutPassword data = new DataWithoutPassword("ksenka916");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login with invalid login and valid password.")
    public Response sendPostRequestWithInvalidLogin(){
        DataLogAndPass data = new DataLogAndPass("619aknesk", "619aknesk");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login with valid login and invalid password.")
    public Response sendPostRequestWithInvalidPassword(){
        DataLogAndPass data = new DataLogAndPass("ksenka916", "ksenka916");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login with data of non-existent user.")
    public Response sendPostRequestWithDataOfNonExistentUser(){
        DataLogAndPass data = new DataLogAndPass("kkkkkkkkk", "kkkkkkkkk");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Step("Check status code and response. Method /api/v1/courier/login. Request send with valid data in body.")
    public void checkStatusCodeAndResponseValidData(Response response){
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Step("Check status code and response. Method /api/v1/courier/login. Request send without login/ password in body.")
    public void checkStatusCodeAndResponseWithoutRequiredField(Response response){
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Step("Check status code and response. Method /api/v1/courier/login. Request send with invalid login/password in body.")
    public void checkStatusCodeAndResponseWithInvalidField(Response response){
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Step("Deserialize data from response courier login.")
    public DataFromResponseCourierLogin deserializeDataFromResponse(Response response){
        DataFromResponseCourierLogin dataFromResponseCourierLogin =
                response.body().as(DataFromResponseCourierLogin.class);
        return dataFromResponseCourierLogin;
    }

    @Step("Serialize data from response courier login into String.")
    public String serializeDataFromResponseIntoString(DataFromResponseCourierLogin dataFromResponseCourierLogin){
        Gson gson = new Gson();
        String json = gson.toJson(dataFromResponseCourierLogin).substring(6, 12);
        return json;
    }
}
