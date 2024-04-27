package htt.esportsfantasybe.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig for JWT authentication.
 * @Author: Alberto Plaza Montes.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAutheticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * SecurityFilterChain configuration.
     * @param http HttpSecurity object.
     * @return SecurityFilterChain object.
     * @throws Exception Exception.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/user/login").permitAll()
                .requestMatchers("/api/user/signup").permitAll()
                .requestMatchers("/api/user/googleLogin").permitAll()
                .requestMatchers("/api/user/getPfp").permitAll()
                .requestMatchers("/api/games/getGames").permitAll()
                .requestMatchers("/api/games/getGameIcon").permitAll()
                .requestMatchers("/api/realLeague/getGameRLeagues").permitAll()
                .requestMatchers("/api/realLeague/getRLeagueIcon").permitAll()
                .requestMatchers("")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
