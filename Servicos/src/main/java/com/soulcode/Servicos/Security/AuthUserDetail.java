package com.soulcode.Servicos.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
 * O Spring Security não se comunica diretamente com o nosso model User =(
 * Então devemos criar uma classe que ele conheça para fazer essa comunicação,
 * UserDetails = Guarda informações do contexto de autenticação do usuário (autorizações, habilitado, etc)
 * Abstrai o user do banco de dados para qeu security conheça seus dados, fazemos a ligação do user do springsecurity com o nosso model do projeto.
 * */

    public class AuthUserDetail implements UserDetails { //UserDetails é interface do JPA

    private String login;
    private String password;


    //AQUI VAMOS FAZER O CONSTRUCTOR PARA PODERMOS INSTANCIAR ESSAS INFORMAÇÕES
    public AuthUserDetail(String login, String password){
        this.login =login;
        this.password = password;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
