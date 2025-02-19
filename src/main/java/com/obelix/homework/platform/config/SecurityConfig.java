package com.obelix.homework.platform.config;

import com.obelix.homework.platform.role.Role;
import com.obelix.homework.platform.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/student/**").hasAnyAuthority(Role.ROLE_STUDENT.toString())
                        .requestMatchers("/teacher/**").hasAnyAuthority(Role.ROLE_TEACHER.toString())
                        .requestMatchers("/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.toString())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll())
                .build();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (_, response, _) -> response.sendRedirect(getRedirect());
    }

    private String getRedirect() {
        return "/" + Role.toSimpleString(userDetailsService.getLoggedInUser().getRole());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (_, response, _) -> response.sendRedirect("/login?error=true");

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
