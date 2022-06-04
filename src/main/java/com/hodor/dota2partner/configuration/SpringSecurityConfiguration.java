package com.hodor.dota2partner.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.PostConstruct;

@Configuration
@AllArgsConstructor
@EnableWebSecurity // tell spring to surpass security auto-configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //prePostEnabled should be true to use @PreAuthorized annotation
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorize -> {
                    authorize.antMatchers(
                                    "/css/**",
                                    "/assets/*",
                                    "/api/auth/**",
                                    "/",
                                    "/index",
                                    "/login",
                                    "/signup",
                                    "/error/**"
                            )
                            .permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                //.log
                //.formLogin()
                //.loginPage("/api/auth/login")
                //.successHandler(authenticationSuccessHandler())
                //.and()
                .httpBasic()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedPage("/error/403")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

}
