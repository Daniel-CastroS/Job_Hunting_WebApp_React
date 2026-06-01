package pogra4.be.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtConfig jwtConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                    return configuration;
                }))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ----- Rutas públicas -----
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/empresas/registro").permitAll()
                        .requestMatchers("/api/oferentes/registro").permitAll()
                        .requestMatchers("/api/puestos/ultimos").permitAll()
                        .requestMatchers("/api/puestos/buscar").permitAll()
                        .requestMatchers("/api/puestos/caracteristicas", "/api/puestos/caracteristicas/**").permitAll()

                        // ----- Empresa -----
                        .requestMatchers("/api/empresa/**").hasAuthority("SCOPE_EMPRESA")

                        // ----- Oferente -----
                        .requestMatchers("/api/oferente/**").hasAuthority("SCOPE_OFERENTE")

                        // ----- Admin -----
                        .requestMatchers("/api/admin/**").hasAuthority("SCOPE_ADMIN")

                        // ----- Cualquier otra ruta requiere autenticación -----
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(jwtConfig.getSecretKey()).build();
    }
}
