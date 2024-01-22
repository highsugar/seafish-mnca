package la.seafish.mnca.app.filter;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;
import la.seafish.mnca.app.utils.GlobalUserToken;
import la.seafish.mnca.app.utils.JwtTool;
import la.seafish.mnca.app.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
public class AccessFilter implements Filter {

    private final JwtTool jwtTool;

    private static final List<String> WHITE_LIST=Arrays.asList("app/login");



    public boolean inWhiteList(String uri){
        for (String prefix:WHITE_LIST) {
            if (uri.startsWith(prefix)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Expose-Headers", "content-type,x-hw-service-token, x-hw-secret-id, x-hw-secret-key,x-hw-version,*");
        response.setHeader("Access-Control-Allow-Headers", "*,token");
        response.setHeader("Content-type", "application/json; charset=UTF-8");
        String uri = ((HttpServletRequest) request).getRequestURI();
        log.info(uri);
        if(inWhiteList(uri)){//uri is in white list.
            filterChain.doFilter(request,response);
        }
        else{
            GlobalUserToken globalUserToken=new GlobalUserToken();
            ResponseWrapper checkTokenResponse=checktoken(request,globalUserToken);
            if( checkTokenResponse.isSuccessFlag()){
                filterChain.doFilter(request,response);
            }
            else{
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(JSONObject.toJSONString(checkTokenResponse).getBytes(StandardCharsets.UTF_8));
            }
        }
    }


    public ResponseWrapper checktoken(HttpServletRequest request, GlobalUserToken globalUserToken){
        String token =((HttpServletRequest) request).getHeader("token");
        //check the token if is null
        if(token==null){
            return ResponseWrapper.markFail(20001,"token in header is null",null);
        }
        //parse the token
        Map<String,Object> parsedToken;
        try{
            parsedToken=jwtTool.parseToken(token);
        }catch (ExpiredJwtException e){
            return ResponseWrapper.markFail(10001,"token time out",e);
        }
        catch (Exception e) {
            return ResponseWrapper.markFail(50001,"Error when parsing token",e);
        }
        //set globalusertoken.
        globalUserToken.setMncaid((String) parsedToken.get("mncaid"));
        return ResponseWrapper.markSuccess();

    }


}
