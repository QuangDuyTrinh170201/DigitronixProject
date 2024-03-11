package com.backendserver.DigitronixProject.configurations;

import com.backendserver.DigitronixProject.components.JwtTokenUtil;
import com.backendserver.DigitronixProject.filters.JwtTokenFilter;
import com.backendserver.DigitronixProject.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(requests ->{
                     requests
                             .requestMatchers(
                             String.format("%s/users/login", apiPrefix)
                             ).permitAll()
//                             .requestMatchers(GET,
//                                     String.format("%s/roles", apiPrefix)).hasRole(Role.DIRECTOR)
                             .anyRequest().authenticated();
                 });
         return http.build();
    }
}
