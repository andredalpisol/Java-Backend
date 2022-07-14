package com.soulcode.Servicos.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

//    AQUI VAMOS GERAR O TOKEN, QUE É O TEMPO QUE O CLIENTE FICARA LOGADO/AUTENTICADO, QUANDO TOKEN EXPIRAR, USER PRECISA LOGAR NOVAMENTE E GERA NOVO TOKEN

    public String generateToken(String login){

        return JWT.create().withSubject(login).withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(Algorithm.HMAC512(secret));
    }

    public String getLogin(String token){
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getSubject();
    }


}