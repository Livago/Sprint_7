package client;

import dto.OrderCreateRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderClient extends RestClient {

    private OrderCreateRequest orderCreateRequest;
    public static final String ORDERS_HANDLE = "/api/v1/orders";

    public void setOrder(OrderCreateRequest orderCreateRequest) {
        this.orderCreateRequest = orderCreateRequest;
    }

    @Step("Создать заказ")
    public Response create() {
        return defaultRestSpecification()
                .body(orderCreateRequest)
                .when()
                .post(ORDERS_HANDLE);
    }
    @Step("Получить список заказов")
    public  Response getOrders(){
        return defaultRestSpecification()
                .get(ORDERS_HANDLE);
    }

}
