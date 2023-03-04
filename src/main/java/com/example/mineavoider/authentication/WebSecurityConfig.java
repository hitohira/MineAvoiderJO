package com.example.mineavoider.authentication;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.formLogin(login -> login
    		.loginProcessingUrl("/authenticate")
    		.usernameParameter("userId")
    		.passwordParameter("password")
    		.loginPage("/login")
    		.defaultSuccessUrl("/")
    		.failureUrl("/login?error")
    		.permitAll()
    	).logout(logout -> logout
    		.logoutUrl("/logout")
    		.logoutSuccessUrl("/")
    		.invalidateHttpSession(true)
//    		.deleteCookies("cookie_name is here")
    	).authorizeHttpRequests(authz -> authz
    		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
    		.requestMatchers("/hello").permitAll()
    		.requestMatchers("/register").permitAll()
    		.requestMatchers("/register_confirm").permitAll()
    		.requestMatchers("/register_result").permitAll()
    		.requestMatchers("/admin").hasRole("admin")
    		.anyRequest().authenticated()
    	);
    	return http.build();
    	
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
