import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {

    Courier courier;
    CourierClient courierClient = new CourierClient();

    @Before
    public void setup() {
        courier = Courier.getCourier();
        courierClient.create(courier);
    }

    @After
    public void teardown() {
        courierClient.delete(courier);
    }


    @Test
    @DisplayName("Тест авторизации курьера")
    @Description("Проверка возможности авторизации курьера, возвращаются 200 статус и 'id' в теле ответа")
    public void courierCanLogin(){
        courierClient.login(courier)
                .statusCode(200)
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Тест авторизации курьера с неправильным логином")
    @Description("Проверка невозможности авторизации курьера с неправильным логином, возвращаются 404 статус и " +
            "'Учетная запись не найдена' в теле ответа")
    public void canNotLoginWithWrongLogin() {
        courierClient.loginWithWrongLogin(courier)
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест авторизации курьера с неправильным паролем")
    @Description("Проверка невозможности авторизации курьера с неправильным паролем, возвращаются 404 статус и " +
            "'Учетная запись не найдена' в теле ответа")
    public void canNotLoginWithWrongPassword() {
        courierClient.loginWithWrongPassword(courier)
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест авторизации курьера с неправильным логином и паролем")
    @Description("Проверка невозможности авторизации курьера с неправильным логином и паролем, возвращаются 404 статус и " +
            "'Учетная запись не найдена' в теле ответа")
    public void canNotLoginWithWrongLoginAndPassword() {
        courierClient.loginWithWrongLoginAndPassword()
                .statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест авторизации курьера без логина")
    @Description("Проверка невозможности авторизации курьера без логина, возвращаются 400 статус и " +
            "'Недостаточно данных для входа' в теле ответа")
    public void canNotLoginWithoutLogin() {
        courierClient.login(Courier.getWithoutLogin())
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест авторизации курьера без пароля")
    @Description("Проверка невозможности авторизации курьера без пароля, возвращаются 400 статус и " +
            "'Недостаточно данных для входа' в теле ответа")
    public void canNotLoginWithoutPassword() {
        courierClient.loginWithoutPassword(courier)
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест авторизации курьера без логина и пароля")
    @Description("Проверка невозможности авторизации курьера без заполнения обязательных полей: логина " +
            "и пароля, возвращаются 400 статус и 'Недостаточно данных для входа' в теле ответа")
    public void canNotLoginWithoutAllFields() {
        courierClient.login(Courier.getWithoutLoginAndPassword())
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}