package com.soulcode.Servicos.Config;

import com.soulcode.Servicos.Security.JWTAuthenticationFilter;
import com.soulcode.Servicos.Security.JWTAuthorizationFilter;
import com.soulcode.Servicos.Services.AuthUserDetailsService;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// agrega todas informações de segurança do http e gerencia o user.

@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Override //CONFIGURANDO SERVIÇO
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //carregar a classe userDetailService do banco
        // e informar o que estamos usando para criptografar a senha
    }

    @Override //CONFIGURANDO COMO O SECURITY DEVE ENTENDER A PAGINA
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable(); // Habilita o cors e desabilita o csrf
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils)); //chamado quando acessamos o /login
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils));
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
//               .antMatchers(HttpMethod.GET, "/servicos/**".permitAll()) AQUI É UM EXEMPLO PARA ACESSARMOS ENDPOINT /SERVICOS/QUALQUER COISA USANDO O METODO GET
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //TODA VEZ Q USUARIO ENTRAR TEM QUE MANDAR O TOKEN
    }

    @Bean
    // BEAN serve para fazermos injeção de dependencia, nesse caso esatamos injetando um método, não uma classe
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean //É O QUE PERMITE QUE NOSSA APLICAÇÃO RECEBA REQUISIÇÕES DE OUTRAS APLICAÇÕES
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        )); //metodos permitidos para o front acessar
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //endpoints permitidos para o front acessar
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }}


