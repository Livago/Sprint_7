import client.CourierClient;
import courierapi.CourierBody;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class LoginTest {
    private static CourierBody courierBody;
    private String login = RandomStringUtils.random(7);
    private String password = RandomStringUtils.random(7);
    private String firstname = RandomStringUtils.random(7);
    private static List<ValidatableResponse> couriersData = new ArrayList<>();
    @Before
    public void setUp() {
        courierBody = new CourierBody(new CourierClient());
    }
    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверки возможности авторизаваться курьером при введении валидных данных")
    public void canLoginCourier(){
        ValidatableResponse response = courierBody.create(login, password, firstname);
        courierBody.getId(login, password);
        couriersData.add(response);
        courierBody.login(login, password)
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }
    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка возможности авторизоваться с пустым значением поля login")
    public void loginWithoutLogin(){
        courierBody.login("", password)
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Авторизация без password")
    @Description("Проверка возможности авторизоваться с пустым значением поля password")
    public void loginWithoutPassword(){
        courierBody.login(login, "")
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Авторизация несуществующим пользователем")
    @Description("Проверка валидации параметров при входе, авторизация с несуществующими login,password")
    public void loginWrongLogin(){
        courierBody.login(login, password)
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @After
    public void tearDown() {
        for (ValidatableResponse response : couriersData) {
            courierBody.delete(response);
        }
        couriersData.clear();
    }
}
