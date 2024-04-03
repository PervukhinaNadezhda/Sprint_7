package orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersMethods {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDERS_PATH = "/api/v1/orders";
    private static final String ORDERS_CANCEL_PATH = "/api/v1/orders/cancel";


    @Step("Получить список заказов")
    public Response get() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get(ORDERS_PATH);

    }

    @Step("Создать заказ")
    public Response create(Orders orders) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(orders)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Получить трэк заказа")
    public String getOrderTrack(Response orderResponse) {
        int track = orderResponse.path("track");
        return String.valueOf(track);
    }

    @Step("Отменить заказ")
    public Response cancel(String orderTrack) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .queryParam("track", orderTrack)
                .when()
                .put(ORDERS_CANCEL_PATH);
    }
}
