package com.aaomile.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.aaomile.service.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User
    //     .withUsername("root@123")
    //     .password("{noop}root")
    //     .build();
    //     InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    //     return inMemoryUserDetailsManager;
    // }
    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAthenticationSuccessHandler handler;
    //configuration of authentication for spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //urlconfiguration for which to keep private and which to keep public
        httpSecurity.authorizeHttpRequests(authorize -> {

            // authorize.requestMatchers("/","/signup").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //default login form.. if neended to change any thing related to login form refer below.
        // httpSecurity.formLogin(Customizer.withDefaults());

        //also can change the spring security login form with personalized.
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authentication");
            formLogin.successForwardUrl("/user/after_login");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });


        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });


        //for default oauth2 login option
        // httpSecurity.oauth2Login(Customizer.withDefaults());

        // customized oauth2 configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });
        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
