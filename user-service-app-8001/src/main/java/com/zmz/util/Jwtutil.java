package com.zmz.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


@Component
public class Jwtutil
{

    private static Integer expireTime;

    private static String secret;

    @Value("${token.expiration}")
    public void setExpireTime(Integer timevalue)
    {
        expireTime = timevalue;
    }

    @Value("${token.secret}")
    public void setSecret(String secretValue)
    {
       secret = secretValue;
    }


    /**
     * 根据claims构建一个token
     * @param claims
     * @return
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*expireTime))
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }


    /**
     * 根据claims构建一个token, 并指定多少豪秒后过期
     * @param claims
     * @param expiredTime 多少毫秒过期
     * @return
     */
    public static String generateToken(Map<String, Object> claims, long expiredTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }



    public static String generateToken(Map<String, Object> claims, long expiredTime, String secret) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }


    /**
     * 判断token是否过期，或签名是否不对
     * @return
     */
    public static Boolean isValidate(String token)
    {
        try
        {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }



    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static Object getPropertityFromToken(String token, String propertity) {
        String result;
        try {
            Object o = getClaimsFromToken(token).get(propertity);
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getOpenIdFromToken(String token) {
        Object openid = getPropertityFromToken(token, "openid");
        if(openid != null)
            return openid.toString();
        return null;
    }


    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }



    /**
     * 获得我们封装在 token 中的 token 创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获得我们封装在 token 中的 token 过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 检查当前时间是否在封装在 token 中的过期时间之后，若是，则判定为 token 过期
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }



}
