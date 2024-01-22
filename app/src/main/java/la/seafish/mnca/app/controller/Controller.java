package la.seafish.mnca.app.controller;


import la.seafish.mnca.app.model.login.LoginParam;
import la.seafish.mnca.app.model.login.LoginRegisterParam;
import la.seafish.mnca.app.model.login.LoginRegisterResult;
import la.seafish.mnca.app.service.LoginService;
import la.seafish.mnca.app.utils.GlobalUserToken;
import la.seafish.mnca.app.utils.ResponseWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app")
public class Controller {



    private LoginService loginService;

    public Controller(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping("test")
    public ResponseWrapper test(){
        return loginService.test();
    }

    @PostMapping("login")
    public ResponseWrapper login(@RequestBody @Validated LoginParam param){
        return loginService.login(param);
    }
    @PostMapping("login/register")
    public ResponseWrapper loginRegister(@RequestBody @Validated LoginRegisterParam loginRegisterParam){
        return loginService.loginRegister(loginRegisterParam);
    }


}
