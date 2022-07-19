package com.soulcode.Servicos.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// classe entra em ação ao chamar /login

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager manager, JWTUtils jwUtils){
        this.authenticationManager = manager;
        this.jwtUtils = jwUtils;
    }

    // PRIMEIRO VAI TENTAR FAZER ESSA FUNÇÃO, VERIFICA SE O LOGIN E A SENHA ESTÃO NO SERVIDOR

    @Override
    //aqui é como se fosse um controller, porém de forma bruta, nós que vamos formatar a resposta da requisição e darmos uma resposta;
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class); // aqui vamos mapear uma requisição e transformar num formato de classe
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken // aqui fazemos a autenticação
                    (user.getLogin(), user.getPassword(), new ArrayList<>()));
        } catch (IOException io) {
            throw new RuntimeException(io.getMessage());
        }

    }

    //ESSA FUNÇÃO IRA OCORRER SE A ATTEMP DER CERTO, VAI ENVIAR O LOGIN PARA O FRONT - ANGULAR

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        AuthUserDetail user = (AuthUserDetail) authResult.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername());

        //AQUI VAMOS SETAR HEADER DA RESPOSTA, PARA QUE ELE SEJA ACEITO PELO FRONT (ANGULAR)
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //customizar mensagem de rro de login que falhou
        response.setStatus(401);
        response.setContentType("application/json");

        response.getWriter().write(json());
        response.getWriter().flush();
    }

    String json() { //formatar mensagem de erro
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\" : \"Não autorizado\", "
                + "\"message\": \"Email/senha inválidos\","
                + "\"path\": \"/login\""
                + "}";
    }
}

/**
 * FRONT MANDA {"login": "jr@gmail.com", "password": "12345"}
 * A partir do JSON -> User
 * Tenta realizar autenticação
 *      Caso dê certo:
 *          - Gera o token JWT
 *          - Retorna o token para o FRONT
 */
