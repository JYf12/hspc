package com.example.huashengpc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {
    private static final String SIGN = "!^&%&*!@$*%!!@(&%2ar^2t";

    /**
     * 生成Token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);//过期时间默认为7七天
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * 验证token
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}
