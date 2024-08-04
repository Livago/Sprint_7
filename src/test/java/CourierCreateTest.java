import client.CourierClient;
import courierapi.CourierBody;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
    private static CourierBody courierBody;
    String login = RandomStringUtils.random(7);
    String password = RandomStringUtils.random(7);
    String firstname = RandomStringUtils.random(7);
    static List<ValidatableResponse> couriersData = new ArrayList<>();


    @Before
    public void setUp() {
        courierBody = new CourierBody(new CourierClient());
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера с валидным телом запроса")
    public void canCreateCourier() {
        ValidatableResponse response = courierBody.create(login, password, firstname);
        response.assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
        courierBody.getId(login, password);
        couriersData.add(response);
    }

    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров")
    @Description("Проверка невозможности создания двух курьеров с одинаковым login")
    public void duplicateCourier() {
        ValidatableResponse responsea = courierBody.create("logina", "passworda", "firstname");
        ValidatableResponse responseb = courierBody.create("logina", "passworda", "firstname")
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierBody.getId("logina", "passworda");
        courierBody.delete(responsea);

    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка валидации, создание курьера со значение обязательного поля login = null")
    public void courierWithoutLogin() {
        courierBody.create(null, "password", "firstname")
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка создания курьера без пароля")
    public void courierWithoutPassword() {
        courierBody.create(login, null, "firstname")
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка возможности создание курьера без параметра firstName в теле запроса")
    public void courierWithoutFirstname() {
        ValidatableResponse response = courierBody.create(login, password, null)
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courierBody.getId(login, password);
        couriersData.add(response);
    }

    @AfterClass
    public static void tearDown() {

        for (ValidatableResponse response : couriersData) {
            courierBody.delete(response);
        }
        couriersData.clear();
    }
}
