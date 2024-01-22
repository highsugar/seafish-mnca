package la.seafish.mnca.app.utils;


import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtTool {
    private Integer EXPIRE_SECONDS=15*60;//15min out time limit
    private String SECRET="jkjuhgu71234ijkoteokkjtimwne495k";

    public String creatToken(Map<String,Object> map){
        return Jwts.builder().
                setSubject("UserInfo").setClaims(map)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setExpiration(DateUtil.offsetSecond(new Date(),EXPIRE_SECONDS))
                .compact();
    }

    public Map<String, Object> parseToken(String token) throws Exception{
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }


}
