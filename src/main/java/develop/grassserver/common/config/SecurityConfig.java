package develop.grassserver.common.config;

import develop.grassserver.auth.application.service.JwtService;
import develop.grassserver.common.security.CustomUserDetailsService;
import develop.grassserver.common.security.ExceptionHandlingFilter;
import develop.grassserver.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PERMIT_SWAGGER_URL_ARRAY = {
            "/api-docs/**",
            "/swagger-ui/**"
    };

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ExceptionHandlingFilter(), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_SWAGGER_URL_ARRAY)
                        .permitAll()
                        .requestMatchers("/api/v1/members/check/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/members")
                        .permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/members")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/members",
                                "/api/v1/members/auth",
                                "/api/v1/members/login",
                                "/api/v1/members/auto-login")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/members/{id}/profile-images",
                                "/api/v1/members/{id}/banner-images")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
