package com.obelix.homework.platform.config;

import com.obelix.homework.platform.role.Role;
import com.obelix.homework.platform.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register").permitAll()
                        .requestMatchers("/student/**").hasAnyAuthority(Role.STUDENT.toString(), Role.ADMIN.toString())
                        .requestMatchers("/teacher/**").hasAnyAuthority(Role.TEACHER.toString(), Role.ADMIN.toString())
                        .requestMatchers("/admin/**").hasAnyAuthority(Role.ADMIN.toString())
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    public CommandLineRunner createAdminUser() {
        return _ -> {
            if (!userDetailsService.existsByUsername("admin")) {
                userDetailsService.createDefaultAdmin();
            }
        };
    }
}
