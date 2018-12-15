package com.graduation.config;

import com.graduation.Handler.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    AuthenticationProvider myAuthenticationProvider;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/logins").permitAll()
                .antMatchers("/user/userRegister").permitAll()
                .antMatchers("/pages/**").hasAnyRole("学生","老师")
                .antMatchers("/index/**").hasAnyRole("学生","老师")
                .antMatchers("/user/**").hasAnyRole("学生","老师")
                .antMatchers("/course/**").hasAnyRole("学生","老师")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login.html");

    }
}
