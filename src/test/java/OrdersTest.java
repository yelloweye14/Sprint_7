import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.hasKey;

@RunWith(Parameterized.class)
public class OrdersTest {

    private final String[] color;

    public OrdersTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][] {
                {new String[]{"\"BLACK\""}},
                {new String[]{"\"GREY\""}},
                {new String[]{"\"BLACK\"", "\"GREY\""}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Параметризированный тест создания заказа")
    @Description("Проверка, что при создании заказа можно указать один из цветов — BLACK или GREY; " +
            "можно указать оба цвета; " +
            "можно совсем не указывать цвет, " +
            "а также что, во всех случаях возвращаются 201 статус и 'track' в теле ответа")
    public void orderCanBeCreated() {
        OrderClient order = new OrderClient(color);
        ValidatableResponse response = order.create();
        response.statusCode(201)
                .assertThat().body("$", hasKey("track"));
    }
}