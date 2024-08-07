package dto;

import lombok.Data;

@Data
public class CourierLoginRequest {

    private String login;
    private String password;
    private int id;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}