package com.amaistra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    private UserDetailsService userDetailsService;
    
    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl")UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/", "/static/**", "/img/**").permitAll()
          .antMatchers(HttpMethod.GET, "/posts", "/posts/*").permitAll()
          .antMatchers(HttpMethod.GET, "/reviews", "/orders", "/contacts").permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .formLogin()
          .loginPage("/auth/login").permitAll()
          .defaultSuccessUrl("/")
          .and()
          .logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
          .invalidateHttpSession(true)
          .clearAuthentication(true)
          .deleteCookies("JSESSIONID")
          .logoutSuccessUrl("/auth/login");
    }
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    
    
//  CREATE TABLE usr(
//  id bigint PRIMARY KEY,
//  username VARCHAR(50) NOT NULL,
//  password VARCHAR(255) NOT NULL,
//  role VARCHAR(50) NOT NULL DEFAULT ('USER')
//  );

//    INSERT INTO usr (id, username, password, role) VALUES (1, 'admin', '$2y$12$fBB8H2nrcxrVdzQCWkGQoOyc0ZOZl/LckylBmTez9ylWKjIUMl9XW', 'ADMIN');
//  INSERT INTO usr (id, username, password, role) VALUES (1, 'a', '$2y$12$kvaIxSDOAlDNSS/WRrRr1ei.wxRFsb2wJ6Bz6JBpfGpVe94.A2TXy', 'ADMIN');

    
}