package questy.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import questy.repository.AppUserRepository

@Configuration
class SecurityConfiguration(
    private val userRepository: AppUserRepository,
    private val jwtAuthTokenFilter: JwtAuthTokenFilter
) {
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService() = AppUserDetailsService(userRepository)

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    //Auth
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/register").permitAll()
                    //Project
                    .requestMatchers("/project/**").authenticated()
                    //info
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/error").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session -> session.sessionCreationPolicy(STATELESS) }
            .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic(Customizer.withDefaults())
            .build()
    }

}