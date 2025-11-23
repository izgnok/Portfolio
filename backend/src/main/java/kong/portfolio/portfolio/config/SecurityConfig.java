package kong.portfolio.portfolio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * PasswordEncoder Bean 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security Filter Chain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (REST API용)
                .csrf(AbstractHttpConfigurer::disable)
                
                // 요청 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 공개 API (인증 불필요)
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/profile").permitAll()
                        .requestMatchers("/api/projects/**").permitAll()
                        .requestMatchers("/api/skills/**").permitAll()
                        .requestMatchers("/api/education/**").permitAll()
                        .requestMatchers("/api/certificates/**").permitAll()
                        .requestMatchers("/api/awards/**").permitAll()
                        .requestMatchers("/api/keywords/**").permitAll()
                        .requestMatchers("/api/visitor-logs").permitAll()
                        
                        // 관리자 API (인증 필요)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                
                // HTTP Basic 인증 활성화 (간단한 인증용)
                .httpBasic(basic -> {});

        return http.build();
    }
}
