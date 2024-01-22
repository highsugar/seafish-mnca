package la.seafish.mnca.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import la.seafish.mnca.app.model.login.LoginParam;
import la.seafish.mnca.app.model.login.LoginRegisterParam;
import la.seafish.mnca.app.model.login.LoginRegisterResult;
import la.seafish.mnca.app.model.login.LoginResult;
import la.seafish.mnca.app.utils.GlobalUserToken;
import la.seafish.mnca.app.utils.JwtTool;
import la.seafish.mnca.app.utils.ResponseWrapper;
import la.seafish.mnca.dao.entity.MncaUser;
import la.seafish.mnca.dao.mapper.MncaUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MncaUserMapper mncaUserMapper;

    private final JwtTool jwtTool;


    public ResponseWrapper test(){
        return ResponseWrapper.markSuccess();
    }

    public ResponseWrapper login( LoginParam param){
        MncaUser mncaUser = mncaUserMapper.selectOne(
                new LambdaQueryWrapper<MncaUser>()
                    .select(MncaUser::getMncaId,MncaUser::getPassword)
                    .eq(MncaUser::getMncaId,param.getMncaId())
                    .last("limit 1")
        );
        LoginResult loginResult = new LoginResult();
        HashMap<String,Object> map= new HashMap<>();

        if (mncaUser==null){
            return ResponseWrapper.markFail(10001,"No user",loginResult);
        }
        if (mncaUser.getPassword().equals( param.getPassword() )) {
            map.put("mncaId",mncaUser.getMncaId());
            loginResult.setToken(jwtTool.creatToken(map));
            return ResponseWrapper.markSuccess(loginResult);
        }
        else {
            return ResponseWrapper.markFail(10001,"incorrect password",loginResult);
        }
    }

    public ResponseWrapper loginRegister(LoginRegisterParam loginRegisterParam){
        String newMncaid=loginRegisterParam.getMncaId();
        String newPassword=loginRegisterParam.getPassword();


        MncaUser existedUser=mncaUserMapper.selectOne(new LambdaQueryWrapper<MncaUser>()
                .select(MncaUser::getMncaId, MncaUser::getPassword)
                .eq(MncaUser::getMncaId,newMncaid)
                .last("limit 1")
        );


        LoginRegisterResult result=new LoginRegisterResult();
        result.setMncaId(newMncaid);
        result.setPassword(newPassword);
        //user have existed
        if(existedUser!=null){
            return ResponseWrapper.markFail(10001,"dupicate mncaid",result);
        }
        //generate and insert a new user
        MncaUser newUser = new MncaUser();
        newUser.setMncaId(loginRegisterParam.getMncaId());
        newUser.setPassword(loginRegisterParam.getPassword());

        mncaUserMapper.insert(newUser);
        return ResponseWrapper.markSuccess(result);



    }



}
