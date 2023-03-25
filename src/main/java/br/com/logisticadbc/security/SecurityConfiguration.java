package br.com.logisticadbc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz

                        .antMatchers(HttpMethod.GET, "/viagem/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/viagem/**").hasAnyRole("ADMIN", "MOTORISTA")
                        .antMatchers(HttpMethod.PUT, "/viagem/**").hasAnyRole("ADMIN", "MOTORISTA")
                        .antMatchers(HttpMethod.DELETE, "/viagem/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET, "/usuario/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/usuario/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("ADMIN", "MOTORISTA", "COLABORADOR")
                        .antMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET, "/rota/**").hasAnyRole("ADMIN", "MOTORISTA", "COLABORADOR")
                        .antMatchers(HttpMethod.POST, "/rota/**").hasAnyRole("ADMIN", "COLABORADOR")
                        .antMatchers(HttpMethod.PUT, "/rota/**").hasAnyRole("ADMIN", "COLABORADOR")
                        .antMatchers(HttpMethod.DELETE, "/rota/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET, "/posto/**").hasAnyRole("ADMIN", "MOTORISTA", "COLABORADOR")
                        .antMatchers(HttpMethod.POST, "/posto/**").hasAnyRole("ADMIN", "COLABORADOR")
                        .antMatchers(HttpMethod.PUT, "/posto/**").hasAnyRole("ADMIN", "COLABORADOR")
                        .antMatchers(HttpMethod.DELETE, "/posto/**").hasRole("ADMIN")

                        .antMatchers("/cargo/**").hasRole("ADMIN")

                        .antMatchers(HttpMethod.GET, "/caminhao/**").hasAnyRole("ADMIN", "MOTORISTA", "COLABORADOR")
                        .antMatchers(HttpMethod.POST, "/caminhao/**").hasAnyRole("ADMIN", "COLABORADOR")
                        .antMatchers(HttpMethod.PUT, "/caminhao/**").hasAnyRole("ADMIN", "MOTORISTA")
                        .antMatchers(HttpMethod.DELETE, "/caminhao/**").hasRole("ADMIN")

                        .antMatchers("/auth/usuario-logado").hasAnyRole("ADMIN", "MOTORISTA", "COLABORADOR")

                        .anyRequest().authenticated());

        http.addFilterBefore(new TokenAuthenticationFilter(tokenService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/",
                "/auth",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // SCrypt algoritmo de criptografia
        return new SCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
