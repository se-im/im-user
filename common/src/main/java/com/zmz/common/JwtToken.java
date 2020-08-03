package com.zmz.common;


import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtToken {
    /**
     * 私钥密码，保存在服务器，客户端是不会知道密码的，以防止被攻击
     */

    private static String SECRET = "wqlz";


    /**
     * 加密方式
     */
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    /**
     *  设置过期时间
     */
    private static final int TIME = 60*60*24*365;

    /**
     * 对密钥进行加密
     * @return
     */
    private static Key getkey(){
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        return 	new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }
    /**
     * 生成Token
     *
     * JWT分成3部分：1.头部（header),2.载荷（payload, 类似于飞机上承载的物品)，3.签证（signature)
     *
     * 加密后这3部分密文的字符位数为：
     *  1.头部（header)：36位，Base64编码
     * 	2.载荷（payload)：没准，BASE64编码
     * 	3.签证（signature)：43位，将header和payload拼接生成一个字符串，
     * 							使用HS256算法和我们提供的密钥（secret,服务器自己提供的一个字符串），
     * 							对str进行加密生成最终的JWT
     *
     * @return
     * @throws Exception
     */
    public static String createToken() throws Exception {

        // 签发时间
        Date iatDate = new Date();
        // 设置过期时间 - 设置签发时间5秒钟后为延迟时间，这里只是做测试，实际时间会比这个长很多
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, TIME);
        // 得到过期时间
        Date expirensDate = nowTime.getTime();
        // 组合header
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", "JWT");
        String token = Jwts.builder()
                .setHeaderParams(map)
                .setExpiration(expirensDate)
                .setIssuedAt(iatDate)
                .signWith(SignatureAlgorithm.HS256,getkey()).compact();

        return token;
    }

    /**
     * 解密Token查看其是否合法
     *
     * @param token
     * @return
     */
    public static Claims verifyToken(String token) {
        Claims body = null;
        try {
            body = Jwts.parser().setSigningKey(getkey()).parseClaimsJws(token).getBody();
        }catch (SignatureException e){
            System.out.println("错误");
            return null;
        }catch (ExpiredJwtException e){
            System.out.println("超时");
            return null;
        }
        catch (Exception e ){
            System.out.println("未知错误");
            return null;
        }
        return body;
    }
}