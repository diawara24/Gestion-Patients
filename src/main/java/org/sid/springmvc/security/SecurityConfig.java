package org.sid.springmvc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // {noop} permet de desactiver la verification du mot de pass encoder
        auth.inMemoryAuthentication().withUser("user1").password("{noop}passer").roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password("{noop}passer").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}passer").roles("USER","ADMIN");
    }

    //chercher le user dans la base de donnees
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*Authentification basic basé sur le protocole http
        http.httpBasic();*/
        // spevifier la page de connection
         http.formLogin();
        /*toute requete http necessite une authentification
        http.authorizeRequests().anyRequest().authenticated();*/
        http.authorizeRequests().antMatchers("/ajout**/**","/save**/**","/delete**/**","/edit**/**","/update**/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        // Active la protection contre les attaques de type csrf
        http.csrf();
    }


}
