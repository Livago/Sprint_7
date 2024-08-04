import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;


public class ListOrderTest {

    private static OrderClient orderClient;
    @Before
    public void setUp(){orderClient = new OrderClient();}

    @Test
    @DisplayName("Получение списка заказов(позитив)")
    @Description("Проверка получения списка заказов")
    public void getOrders(){
        orderClient.getOrders()
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue())
                .body("orders", hasSize(greaterThan(0)));
    }

}
