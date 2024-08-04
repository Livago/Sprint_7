package client;

import dto.CourierCreateRequest;
import dto.CourierLoginRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierClient extends RestClient {
    public static final String CREATE_COURIER_HANDLE = "/api/v1/courier/";
    public static final String LOGIN_COURIER_HANDLE = "/api/v1/courier/login";
    public static final String DELETE_COURIER_HANDLE = "/api/v1/courier/{id}";
    private static int courierId;
    @Step("Создать курьера")
    public Response create(CourierCreateRequest courierCreateRequest) {
        return defaultRestSpecification()
                .body(courierCreateRequest)
                .when()
                .post(CREATE_COURIER_HANDLE);
    }
    @Step("Авторизоваться курьером")
    public Response login(CourierLoginRequest courierLoginRequest){
        return defaultRestSpecification()
                .body(courierLoginRequest)
                .when()
                .post(LOGIN_COURIER_HANDLE);
    }

    @Step("Получить id")
    public int getId(CourierLoginRequest courierLoginRequest){
        Response response = defaultRestSpecification()
                .body(courierLoginRequest)
                .when()
                .post(LOGIN_COURIER_HANDLE);
        courierId = response.then().extract().path("id");
        return courierId;
    }
    @Step("Удалить курьера")
    public Response deleteCourier() {
        return defaultRestSpecification()
                .when()
                .delete(DELETE_COURIER_HANDLE, courierId);
    }
}
