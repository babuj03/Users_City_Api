package com.user.info.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {
    	
    	 auth.inMemoryAuthentication()
         .withUser("jayee").password("{noop}password").roles("ADMIN")
         .and()
         .withUser("test").password("{noop}password").roles("ADMIN");
 
    }
    
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
          .anyRequest().authenticated()
          .and().httpBasic();
    }
}