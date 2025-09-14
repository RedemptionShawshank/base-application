package com.example.SpringSecurity.config;

import org.apache.tomcat.websocket.BasicAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/data/user_info1").permitAll()
                        .requestMatchers("/data/get_info1").permitAll()
                        .requestMatchers("/data/user_info2").permitAll()
                        .requestMatchers("/data/get_info2").permitAll()
                        .requestMatchers("/auth").hasRole("USER")// 👈 use /** so nested paths work
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // disable CSRF (needed for H2)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // 👈 allow iframes from same origin
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));


        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails u1 = User.withUsername("shashank")
//                .password(new BCryptPasswordEncoder().encode("mypass1"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails u2 = User.withUsername("nikhil")
//                .password(new BCryptPasswordEncoder().encode("mypass2"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(u1,u2);
//    }
}
