package com.soulcode.Servicos.Security;

import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

//Entra em ação em todos endpoint/requisição que estão protegidos
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtils jwtUtils;

    public JWTAuthorizationFilter(AuthenticationManager manager, JWTUtils jwtUtils){
        super(manager);
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String token = request.getHeader("Authorization"); // AQUI RECEBEMOS UMA RESPOSTA QUE POR PADRÃO VEM COM A PALVRA "BEARER"
    if (token!= null && token.startsWith("Bearer")){ // TOKEN "VALIDO"
        UsernamePasswordAuthenticationToken authToken = getAuthentication(token.substring(7)); // 7 porque é o comprimento de bearer, vamos retirar ele pra ver se bate com o do usuario

        if (authToken != null){
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Guarda informações do usuário autenticado no contexto do Spring
            // Essa informação pode ser utilizada dentro dos controllers da aplicação
        }
    }

    chain.doFilter(request, response);

//    GUARD DO ANGULAR USAR ESSA FUNÇÃO?
    }


    public UsernamePasswordAuthenticationToken getAuthentication (String token){
        String login = jwtUtils.getLogin(token);

        if (login == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(login, null, new ArrayList<>());
    }
}
