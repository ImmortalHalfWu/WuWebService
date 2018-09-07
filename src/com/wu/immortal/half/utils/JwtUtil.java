package com.wu.immortal.half.utils;

import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkImpl;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String ISS = "immortalHalfWu";
    private static final String SECRET = "**OrM7E2Fh7*HgQ";
    private static final byte[] SECRET_BYTES = SECRET.getBytes();

    private static final String KEY_PHONE = "phone";
    private static final String KEY_USER_ID = "userId";
    private static final String HA_256 = "HmacSHA256";

    public static void tokenTest() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        long endMillis = calendar.getTimeInMillis();
        String newToken = createNewToken(new TokenInfoBean("", "13613571331", 1, endMillis));
        System.out.println(newToken);
        System.out.println(parseToken(newToken).toString());
    }

    /**
     * 生成新的token
     * @return token
     */
    public static String createNewToken(TokenInfoBean tokenInfoBean) {
        LogUtil.i(tokenInfoBean);

        String phone = tokenInfoBean.getPhone();
        int userId = tokenInfoBean.getUserId();
        long endMillis = tokenInfoBean.getEndMilles();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = DataUtil.getNowTimeToLong();//生成JWT的时间
        Date now = new Date(nowMillis);

        Map<String,Object> claims = new HashMap<>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put(KEY_USER_ID, userId);
        claims.put(KEY_PHONE, phone);

        SecretKey secretKey = new SecretKeySpec(SECRET_BYTES, "HmacSHA256"); //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()//这里其实就是new一个JwtBuilder，设置jwt的body
                .setIssuer(ISS)                // 签发人
//                .setSubject(subject.toJSONString())        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setAudience(phone)                // 接收人
//                .setId(String.valueOf(nowMillis))//设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .signWith(signatureAlgorithm, secretKey);//设置签名使用的签名算法和签名使用的秘钥
        if (endMillis >= 0) {
            long expMillis = nowMillis + endMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //设置过期时间
        }
        return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt

    }

    private static TokenInfoBean parseToken(String token) {

        Jws<Claims> headerClaimsJwt = Jwts.parser().setSigningKey(new SecretKeySpec(SECRET_BYTES, HA_256)).parseClaimsJws(token);
        Claims body = headerClaimsJwt.getBody();

        String phone = body.getOrDefault(KEY_PHONE, "").toString();
        int userId = (int) body.getOrDefault(KEY_USER_ID, 0);
        long endMilles = body.getExpiration().getTime();

        return new TokenInfoBean(token, phone, userId, endMilles);
    }


    /**
     * @return 验证token是否有效， 有效则返回token数据，无效则直接回复前端并返回null
     */
    public static @Nullable
    TokenInfoBean authToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 解析token
        String token = request.getHeader(FinalUtil.HEADER_KEY_ACCESS);
        LogUtil.i("解析token：");
        if (FinalUtil.checkNull(token)) {
            LogUtil.e("token 为 null");
            //  token 异常
            RequestUtil.callBackResult(ResultBean.REQUEST_ERRO_TOKEN_ILLEGAL, response, JsonWorkImpl.newInstance());
            return null;
        }

        // token认证
        TokenInfoBean tokenInfoBean = JwtUtil.parseToken(token);
        if (tokenInfoBean.checkNull()) {
            LogUtil.e("token 数据不完整" + tokenInfoBean.toString());
            //  token 异常
            RequestUtil.callBackResult(ResultBean.REQUEST_ERRO_TOKEN_ILLEGAL, response, JsonWorkImpl.newInstance());
            return null;
        }

        if (tokenInfoBean.getEndMilles() < DataUtil.getNowTimeToLong()) {
            LogUtil.e("token 失效" + tokenInfoBean.toString());
            //  token 失效
            RequestUtil.callBackResult(ResultBean.REQUEST_ERRO_TOKEN_END_TIME, response, JsonWorkImpl.newInstance());
            return null;
        }
        LogUtil.i("解析token成功：" + token + "\n" + tokenInfoBean.toString());
        return tokenInfoBean;
    }
}
