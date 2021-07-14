package org.sid.springmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("*************************************************");
        System.out.println(passwordEncoder.encode("passer"));
        System.out.println("*************************************************");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username as principal, password as credentials, active FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username as principal, role as role FROM users_roles WHERE username=?")
                .passwordEncoder(passwordEncoder)
                .rolePrefix("ROLE_");

    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //chercher le user dans la base de donnees
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // specifier la page de connection
        http.formLogin().loginPage("/login");
        /*Authentification basic basé sur le protocole http
        http.httpBasic();*/
        http.authorizeRequests().antMatchers("/admin**/**","/ajout**/**","/save**/**","/delete**/**","/edit**/**","/update**/**","/form**/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/user**/**","/login**/**").permitAll();
        //toute requete http necessite une authentification
        http.authorizeRequests().anyRequest().authenticated();
        // Personaliser la page d'erreur
        http.exceptionHandling().accessDeniedPage("/errorNotAuthorized");
        // http.csrf();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // {noop} permet de desactiver la verification du mot de pass encoder
        *//*auth.inMemoryAuthentication().withUser("user1").password("{noop}passer").roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password("{noop}passer").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}passer").roles("USER","ADMIN");*//*

        // Connection via le JDBC
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("***********************************");
        System.out.println(passwordEncoder.encode("passer"));
        System.out.println("***********************************");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username as principal, password as credentials, active FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username as principal, role as role FROM users_roles WHERE username=?")
                .passwordEncoder(passwordEncoder)
                .rolePrefix("ROLE_");
    }*/

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // {noop} permet de desactiver la verification du mot de pass encoder
        *//*auth.inMemoryAuthentication().withUser("user1").password("{noop}passer").roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password("{noop}passer").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}passer").roles("USER","ADMIN");*//*

        // Connection via le JDBC
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("*************************************************");
        System.out.println(passwordEncoder.encode("passer"));
        System.out.println("*************************************************");
        *//*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username as principal, password as credentials, active FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username as principal, role as role FROM users_roles WHERE username=?")
                .passwordEncoder(passwordEncoder)
                .rolePrefix("ROLE_");*//*
    }*/

}
