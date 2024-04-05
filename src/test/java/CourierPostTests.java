import courier.Courier;
import courier.CourierGenerator;
import courier.CourierMethods;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CourierPostTests {

    private Courier courier;
    private CourierMethods courierMethods;

    @Before
    public void createTestData() {
        courierMethods = new CourierMethods();
        courier = CourierGenerator.random();
    }


    @Test
    @Description("Проверка возможности создания курьера. В теле запроса переданы все обязательные поля. " +
            "Проверяется код ответа. " +
            "Проверяется тело ответа.")
    public void courierCanBeCreated() {
        Response response = courierMethods.create(courier);

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        cleanUp();
    }

    @Test
    @Description("Проверка, что нельзя создать двух одинаковых курьеров. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void sameCourierCantBeCreated() {
        courierMethods.create(courier);
        Response responseSecond = courierMethods.create(courier);

        responseSecond.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        cleanUp();
    }

    @Test
    @Description("Проверка, что нельзя создать курьера без логина. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void courierCantBeCreatedWithLoginNull() {
        courier.setLogin(null);
        Response response = courierMethods.create(courier);

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @Description("Проверка, что нельзя создать курьера без пароля. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void courierCantBeCreatedWithPasswordNull() {
        courier.setPassword(null);
        Response response = courierMethods.create(courier);

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    public void cleanUp() {
        String courierId = courierMethods.getCourierId(courierMethods, courier);
        courierMethods.delete(courierId);
    }

}