import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {

    @Test
    @DisplayName("Тест возврата списка заказов")
    @Description("Проверка возврата списка заказов в теле ответа")
    public void orderCanBeCreated() {
        OrderClient order = new OrderClient();
        ValidatableResponse response = order.getListOfOrders();
        response.assertThat().body("orders", notNullValue());
    }
}


