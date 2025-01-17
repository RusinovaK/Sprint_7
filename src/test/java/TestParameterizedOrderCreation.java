import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ordercreation.data.ValidData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestParameterizedOrderCreation {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List <String> color;

    public TestParameterizedOrderCreation(String firstName, String lastName, String address, int metroStation,
                                          String phone, int rentTime, String deliveryDate, String comment, List <String> color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList("BLACK")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList("GRAY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList("BLACK", "GRAY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList()}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Create order and check status code and response")
    @Description("Check status code and response of /api/v1/orders (order creation method). Send request with valid data in body.")
    public void createOrderAndCheckStatusCodeAndResponse(){
        Response response = sendPostRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        checkStatusCodeAndResponse(response);
    }

    @Step("Send POST request to /api/v1/orders with valid data")
    public Response sendPostRequest(String firstName, String lastName, String address, int metroStation,
                                    String phone, int rentTime, String deliveryDate, String comment, List <String> color){
        ValidData data = new ValidData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(data)
                        .when()
                        .post("/api/v1/orders");
        return response;
    }

    @Step("Check status code and response. Method /api/v1/orders. Request send with valid data in body.")
    public void checkStatusCodeAndResponse(Response response){
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
