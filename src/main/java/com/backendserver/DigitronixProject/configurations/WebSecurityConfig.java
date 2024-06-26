package com.backendserver.DigitronixProject.configurations;

import com.backendserver.DigitronixProject.components.JwtTokenUtil;
import com.backendserver.DigitronixProject.filters.JwtTokenFilter;
import com.backendserver.DigitronixProject.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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
                                     String.format("%s/users/login", apiPrefix),
                                     String.format("%s/categories/getAll", apiPrefix),
                                     String.format("%s/products/images/**", apiPrefix),

                                     String.format("%s/material_categories/getAll", apiPrefix)
                             ).permitAll()
                             .requestMatchers(GET,
                                     String.format("%s/categories/**", apiPrefix),
//                                     String.format("%s/products/**", apiPrefix),
                                     String.format("%s/materials/images/**", apiPrefix),
                                     String.format("%s/materials/**", apiPrefix)).permitAll()
//                             .requestMatchers(GET,
//                                     String.format("%s/roles", apiPrefix)).hasRole(Role.DIRECTOR)
                             .anyRequest().authenticated();
                 });

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
