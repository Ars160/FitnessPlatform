package org.example.fitnessplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/register_page", "/login_page").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                        .requestMatchers("/home_page").permitAll() // Разрешаем доступ ко всем пользователям
                        .requestMatchers("/admin-panel").hasRole("ADMIN") // Доступ для администраторов
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/authentificate/sign_in")
                        .loginProcessingUrl("/authentificate/auth")
                        .usernameParameter("user_email")
                        .passwordParameter("user_password")
                        .failureUrl("/authentificate/sign_in?error=true")
                        .defaultSuccessUrl("/authentificate/profile", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/authentificate/logout")
                        .logoutSuccessUrl("/authentificate/sign_in")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
