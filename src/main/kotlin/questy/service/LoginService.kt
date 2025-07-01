package questy.service

import org.springframework.stereotype.Service
import questy.dto.auth.LoginRequest
import questy.dto.auth.LoginResponse
import questy.logger
import questy.repository.AppUserRepository
import questy.security.JwtAuthFacade

@Service
class LoginService(
    private val userRepository: AppUserRepository,
    private val jwtFacade: JwtAuthFacade
) {
    val log = logger()

    fun login(loginRequest: LoginRequest): LoginResponse {
        val token = jwtFacade.authenticateAndGenerateToken(loginRequest)
        log.info { "JWT token generated : $token" }

        return token
    }
}
