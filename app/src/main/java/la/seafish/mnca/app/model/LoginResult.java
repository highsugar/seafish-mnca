package la.seafish.mnca.app.model;


import lombok.Data;

@Data
public class LoginResult {
    private String token;

    public LoginResult(String token) {
        this.token = token;
    }

    public LoginResult() {
    }
}
