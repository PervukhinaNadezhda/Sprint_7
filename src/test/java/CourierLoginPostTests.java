import courier.Courier;
import courier.CourierGenerator;
import courier.CourierLogin;
import courier.CourierMethods;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierLoginPostTests {

    private Courier courier;
    private CourierMethods courierMethods;

    @Before
    public void createTestData() {
        courierMethods = new CourierMethods();
        courier = CourierGenerator.random();
        Response response = courierMethods.create(courier);
    }

    @After
    public void cleanUp() {
        String courierId = courierMethods.getCourierId(courierMethods, courier);
        courierMethods.delete(courierId);
    }

    @Test
    @Description("Проверка, что созданный курьер может авторизоваться. В теле запроса переданы все обязательные поля. " +
            "Проверяется код ответа. " +
            "Проверяется, что тело ответа не пустое.")
    public void courierCanBeLogin() {
        Response loginResponse = courierMethods.login(CourierLogin.from(courier));

        loginResponse.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @Description("Проверка, что при неправильном логине система вернет ошибку. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void courierLoginWithWrongLogin() {
        Courier newCourier = CourierGenerator.random();

        Response loginResponse = courierMethods.login(new CourierLogin(newCourier.getLogin(), courier.getPassword()));

        loginResponse.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @Description("Проверка, что при неправильном пароле система вернет ошибку. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void courierLoginWithWrongPassword() {
        Courier newCourier = CourierGenerator.random();

        Response loginResponse = courierMethods.login(new CourierLogin(courier.getLogin(), newCourier.getPassword()));

        loginResponse.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @Description("Проверка, что при отсутствии логина система вернет ошибку. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void courierLoginWithLoginNull() {
        Courier newCourier = CourierGenerator.random();
        newCourier.setLogin(null);

        Response loginResponse = courierMethods.login(CourierLogin.from(newCourier));

        loginResponse.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @Description("Проверка, что при отсутствии пароля система вернет ошибку. " +
            "Проверяется код ответа. " +
            "Проверяется текст ошибки в теле ответа.")
    public void courierLoginWithPasswordNull() {
        Courier newCourier = CourierGenerator.random();
        newCourier.setPassword(null);

        Response loginResponse = courierMethods.login(CourierLogin.from(newCourier));

        loginResponse.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

}