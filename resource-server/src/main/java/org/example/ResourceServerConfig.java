package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Engineering Lesson: Standard prefix for OAuth2 scopes is "SCOPE_"
                        .requestMatchers("/api/admin/**").hasAuthority("SCOPE_write")
                        .requestMatchers("/api/user/**").hasAuthority("SCOPE_read")
                        .requestMatchers("/api/service/**").authenticated()
                        .anyRequest().authenticated()
                )
                // This line tells Spring to expect a JWT in the 'Authorization' header
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}