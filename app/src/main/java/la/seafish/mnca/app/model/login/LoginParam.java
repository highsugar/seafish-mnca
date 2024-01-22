package la.seafish.mnca.app.model.login;

import com.sun.istack.internal.NotNull;
import lombok.Data;


@Data
public class LoginParam {
    @NotNull
    private String mncaId;
    @NotNull
    private String password;

    public LoginParam(String mncaId, String password) {
        this.mncaId = mncaId;
        this.password = password;
    }
}
