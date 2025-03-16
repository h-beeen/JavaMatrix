package kr.beeen.matrix.global.security.config;

import kr.beeen.matrix.global.filter.GlobalExceptionHandlingFilter;
import kr.beeen.matrix.global.security.filter.GlobalAuthorizationFilter;
import kr.beeen.matrix.global.security.matcher.UnauthorizedRequestMatchers;
import kr.beeen.matrix.global.security.provider.MatrixAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final GlobalAuthorizationFilter authorizationFilter;
    private final GlobalExceptionHandlingFilter exceptionHandlingFilter;

    @Bean
    @Order(0)
    public SecurityFilterChain unauthorizedFilterChain(HttpSecurity http) throws Exception {
        http.securityMatchers(matchers -> matchers.requestMatchers(UnauthorizedRequestMatchers.getMatchers()))
                .authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .addFilterBefore(exceptionHandlingFilter, UsernamePasswordAuthenticationFilter.class);

        return commonHttpSecurity(http).build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizedFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .addFilterBefore(exceptionHandlingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return commonHttpSecurity(http).build();
    }

    private HttpSecurity commonHttpSecurity(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    public AuthenticationManager authenticationManager(MatrixAuthenticationProvider authenticationProvider) {
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}

