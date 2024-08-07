package dto;

import lombok.Data;

@Data
public class CourierCreateRequest {

    private String login;
    private String password;
    private String firstName;
    private String id;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setLogin(String login) {
        this.login = login;
    }
}