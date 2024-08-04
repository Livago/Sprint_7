package courierapi;

import client.CourierClient;
import dto.CourierCreateRequest;
import dto.CourierLoginRequest;
import io.restassured.response.ValidatableResponse;

public class CourierBody {
    private final CourierClient courierClient;

    public CourierBody(CourierClient courierClient) {
        this.courierClient = courierClient;
    }

    public ValidatableResponse create(String login, String password, String firstname){
        CourierCreateRequest request = new CourierCreateRequest();
        request.setLogin(login);
        request.setPassword(password);
        request.setFirstName(firstname);
        return courierClient.create(request).then();
    }

    public ValidatableResponse login(String login, String password){
        CourierLoginRequest request = new CourierLoginRequest();
        request.setLogin(login);
        request.setPassword(password);
        return courierClient.login(request).then();
    }

    public ValidatableResponse delete(ValidatableResponse response) {

        return courierClient.deleteCourier().then();
    }

    public int getId(String login, String password){
        CourierLoginRequest courierId = new CourierLoginRequest();
        courierId.setLogin(login);
        courierId.setPassword(password);
        return courierClient.getId(courierId);
    }
}
