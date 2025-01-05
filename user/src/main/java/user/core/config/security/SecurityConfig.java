package user.core.config.security;

import jakarta.servlet.DispatcherType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import user.security.jwt.service.TokenHelperService;
import user.security.jwt.filter.JwtAuthFilter;
import user.security.service.AuthorizationService;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthorizationService authorizationService;
    private final TokenHelperService tokenHelperService;

    private final List<String> WHITE_LIST = List.of("/open-api/**");

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(cors -> cors.disable())
            .addFilterBefore(
                new JwtAuthFilter(authorizationService, tokenHelperService)
                , UsernamePasswordAuthenticationFilter.class
            )
            .csrf(csrfConfig -> csrfConfig.disable())
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(it ->
                it.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    .dispatcherTypeMatchers(DispatcherType.ERROR)
                    .permitAll()
                    .requestMatchers(WHITE_LIST.toArray(new String[0]))
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .httpBasic(basic -> basic.authenticationEntryPoint(authenticationEntryPoint))
            .exceptionHandling(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
            .ignoring()
            .requestMatchers(WHITE_LIST.toArray(new String[0]));
    }

}
