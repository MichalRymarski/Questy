package questy.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import questy.repository.AppUserRepository

@Configuration
class AppUserDetailsServiceConfiguration(
    private val userRepository: AppUserRepository,
) {
    @Bean
    fun userDetailsService() = AppUserDetailsService(userRepository)
}