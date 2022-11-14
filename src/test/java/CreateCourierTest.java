import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    Courier courier;
    CourierClient courierClient;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @After
    public void teardown() {
        courierClient.delete(courier);
    }


    @Test
    @DisplayName("Тест создания курьера")
    @Description("Проверка возможности создания нового курьера при заполнении всех необходимых полей, возвращаются 201 статус и 'ok: true' в теле ответа")
    public void courierCanBeCreated(){
        courier = Courier.getCourier();
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Тест повторного создания курьера с одинаковым логином")
    @Description("Проверка невозможности создания двух одинаковых курьеров, возвращаются 409 статус и 'Этот логин " +
            "уже используется. Попробуйте другой' в теле ответа")
    public void canNotCreateTwoSameCouriers() {
        courier = Courier.getCourier();
        courierClient.create(courier);
        ValidatableResponse sameCourierResponse = courierClient.create(courier);
        sameCourierResponse.statusCode(409)
                            .assertThat().body("message", equalTo("Этот логин уже используется. " +
                            "Попробуйте другой."));
    }

    @Test
    @DisplayName("Тест создания курьера без заполненных полей Логин и Пароль")
    @Description("Проверка невозможности создания курьера без заполненения обязательных полей Логин и Пароль, " +
            "возвращаются статус 400 и 'Недостаточно данных для создания учетной записи' в теле ответа")
    public void canNotCreateCourierWithoutAllFields() {
        ValidatableResponse response = courierClient.create(Courier.getWithoutLoginAndPassword());
        response.statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для " +
                        "создания учетной записи"));
    }

    @Test
    @DisplayName("Тест создания курьера без заполненного поля Логин")
    @Description("Проверка создания курьера без одного, обязательного для заполнения поля - Логин" +
            "возвращаются статус 400 и 'Недостаточно данных для создания учетной записи' в теле ответа")
    public void canNotCreateCourierWithoutLogin() {
        ValidatableResponse response = courierClient.create(Courier.getWithoutLogin());
        response.statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для " +
                        "создания учетной записи"));
    }

    @Test
    @DisplayName("Тест создания курьера без заполненного поля Пароль")
    @Description("Проверка создания курьера без одного, обязательного для заполнения поля - Пароль" +
            "возвращаются статус 400 и 'Недостаточно данных для создания учетной записи' в теле ответа")
    public void canNotCreateCourierWithoutPassword() {
        ValidatableResponse response = courierClient.create(Courier.getWithoutPassword());
        response.statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для " +
                        "создания учетной записи"));
    }
}