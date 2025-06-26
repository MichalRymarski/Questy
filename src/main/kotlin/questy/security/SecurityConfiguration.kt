package questy.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { abstractHttpConfigurer -> abstractHttpConfigurer::disable }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/actuator/health").permitAll()
                    .anyRequest().permitAll() //TODO: Change to authenticated() when security is implemented
            }
            .sessionManagement { session -> session.sessionCreationPolicy(STATELESS) }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

}