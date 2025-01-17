import getlistoforders.data.DataFromResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetListOfOrders {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Get list of orders without body and queryParams and check status code and response")
    @Description("Check status code and response of /api/v1/orders (get list of orders method) if send request without body and queryParams.")
    public void getListOfOrdersWithoutBodyAndQueryParamsAndCheckStatusCodeAndResponse(){
        Response response = sendGetRequest();
        checkStatusCode(response);
        DataFromResponse dataFromResponse = deserializeDataFromResponse(response);
        checkResponse(dataFromResponse);
    }

    @Step("Send GET request to /api/v1/orders without body and queryParams.")
    public Response sendGetRequest(){
        Response response =
                given()
                        .get("/api/v1/orders");
        return response;
    }

    @Step("Deserialize data from response to get list of orders without body and queryParams.")
    public DataFromResponse deserializeDataFromResponse(Response response){
        DataFromResponse dataFromResponse =
                response.body().as(DataFromResponse.class);
        return dataFromResponse;
    }

    @Step("Check status code. Method /api/v1/orders. Request send without body and queryParams.")
    public void checkStatusCode(Response response){
        response.then().statusCode(200);
    }

    @Step("Check response. Method /api/v1/orders. Request send without body and queryParams.")
    public void checkResponse(DataFromResponse dataFromResponse){
        assertThat(dataFromResponse.getOrders(), notNullValue());
    }
}
