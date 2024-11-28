package com.backend.proyectointegradorc1g6.security;

import com.backend.proyectointegradorc1g6.security.filter.JwtAuthenticationFilter;
import com.backend.proyectointegradorc1g6.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.GET, "/users/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .antMatchers(HttpMethod.POST, "/users/create").hasRole("USER")
                        .antMatchers(HttpMethod.PUT, "/users/update/{id}").hasAnyRole("ADMIN", "USER")
                        .antMatchers(HttpMethod.PUT, "/users/update/privilege/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/users/delete/{id}").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET, "/categories/**", "/characteristics/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/categories/register", "/characteristics/register").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/categories/update/{id}", "/characteristics/update/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/categories/delete/{id}", "/characteristics/delete/{id}").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET, "/autos/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/autos/register").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/autos/registers3").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/autos/update/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/autos/updates3/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/autos/delete/{id}").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/mail/send/**").permitAll()

                        .antMatchers(HttpMethod.GET, "/reservations/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/reservations/register").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/reviews/register").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/reviews/delete/{id}").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/reviews/list/byuser/{usuarioId}").hasRole("USER")
                        .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }

}
