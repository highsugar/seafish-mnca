package la.seafish.mnca.app.model.login;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRegisterParam {
    @NotNull
    private String mncaId;
    @NotNull
    private String password;
}
