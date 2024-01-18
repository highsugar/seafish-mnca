package la.seafish.mnca.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysql.cj.log.Log;
import la.seafish.mnca.app.model.LoginParam;
import la.seafish.mnca.app.model.LoginResult;
import la.seafish.mnca.app.utils.ResponseWrapper;
import la.seafish.mnca.dao.entity.MncaUser;
import la.seafish.mnca.dao.mapper.MncaUserMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MncaUserMapper mncaUserMapper;


    public ResponseWrapper test(){
        return ResponseWrapper.markSuccess();
    }

    public ResponseWrapper login(LoginParam param){
        MncaUser mncaUser = mncaUserMapper.selectOne(
                new LambdaQueryWrapper<MncaUser>()
                    .select(MncaUser::getMncaId,MncaUser::getPassword)
                    .eq(MncaUser::getMncaId,param.getMncaId())
                    .last("limit 1")
        );
        LoginResult loginResult = new LoginResult();
        if (mncaUser==null){
            return ResponseWrapper.markFail(10001,"No user",false,loginResult);
        }
        if (mncaUser.getPassword().equals( param.getPassword() )) {
            return ResponseWrapper.markSuccess(loginResult);
        }
        else {
            return ResponseWrapper.markFail(10001,"incorrect password",false,loginResult);
        }

    }



}
