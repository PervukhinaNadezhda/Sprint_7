import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import orders.Orders;
import orders.OrdersGenerator;
import orders.OrdersMethods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersPostTests {

    private Orders orders;
    private OrdersMethods ordersMethods;
    private Response orderResponse;

    private final String testName;
    private final String[] color;

    public OrdersPostTests(String testName, String[] color) {
        this.testName = testName;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] setParamColor() {
        return new Object[][]{
                {"Черный", new String[]{"BLACK"}},
                {"Серый", new String[]{"GREY"}},
                {"Черный и Серый", new String[]{"BLACK", "GREY"}},
                {"Не указан", new String[]{}},
        };
    }

    @Before
    public void createTestData() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        ordersMethods = new OrdersMethods();
        orders = OrdersGenerator.random();
    }

    @After
    public void cleanUp() {
        String orderTrack = ordersMethods.getOrderTrack(orderResponse);
        ordersMethods.cancel(orderTrack);
    }

    @Test
    @Description("Проверка возможности создания заказа с разными цветами самоката. " +
            "Проверяется код ответа. " +
            "Проверяется тело ответа.")
    public void orderCanBeCreated() {
        orders.setColor(color);
        orderResponse = ordersMethods.create(orders);

        orderResponse.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

}