import client.OrderClient;
import dto.OrderCreateRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {

    private static OrderClient orderClient;
    private OrderCreateRequest orderCreateRequest;

    public OrderTest(OrderCreateRequest orderCreateRequest) {
        this.orderCreateRequest = orderCreateRequest;
    }

    @Parameterized.Parameters
    public static Object[][] testOrderData() {
        return new Object[][]{
                {new OrderCreateRequest("Greg", "Gregov", "Moscow", "test", "89999111222", 12, "2024-12-12", "KAIF", new String[]{"BLACK"})},
                {new OrderCreateRequest("Иван", "Ivanov", "Москва", "0", "+7 999 999 99 99", 2, "2024-01-01", "Кайф", new String[]{"GREY"})},
                {new OrderCreateRequest("Иван", "Иванов", "Санкт-петербург", "1", "+12345678912", 5, "2024-02-02", "Хех", new String[]{"BLACK", "GREY"})},
                {new OrderCreateRequest("Астра", "линукс", "алдпро", "Варшавское", "1231123123", 1, "2024-04-04", "го", new String[]{})},
        };
    }

    @BeforeClass
    public static void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void createOrder() {
        orderClient.setOrder(orderCreateRequest);
        orderClient.create().then().statusCode(SC_CREATED).and().assertThat().body("track", notNullValue());
    }
}