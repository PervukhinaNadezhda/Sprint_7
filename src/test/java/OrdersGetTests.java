import io.qameta.allure.Description;
import io.restassured.response.Response;
import orders.OrdersMethods;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersGetTests {

    private OrdersMethods ordersMethods;

    @Test
    @Description("Проверка, что на запрос GET /api/v1/orders получаем список заказов. " +
            "Проверяется код ответа. " +
            "Проверяется, что тело ответа не пустое.")
    public void checkOrderList() {
        ordersMethods = new OrdersMethods();
        Response response = ordersMethods.get();

        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

}