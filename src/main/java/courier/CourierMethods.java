package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierMethods {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    @Step("Создать курьера")
    public Response create(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Логин курьера")
    public Response login(CourierLogin courierLogin) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Удалить курьера")
    public Response delete(String courierId) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_PATH + courierId);
    }

    @Step("Получить id курьера")
    public String getCourierId(CourierMethods courierMethods, Courier courier) {
        Response loginResponse = courierMethods.login(CourierLogin.from(courier));
        int id = loginResponse.path("id");
        return String.valueOf(id);
    }

}
