package questy.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import questy.dto.auth.LoginRequest
import questy.dto.auth.LoginResponse
import questy.exception.type.TokenCreationException
import questy.logger
import questy.repository.AppUserRepository
import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class JwtAuthFacade(
    private val authenticationManager: AuthenticationManager,
    private val properties: JwtConfigProperties,
    private val userRepository: AppUserRepository,
    private val clock: Clock
) {
    val log = logger()

    @Throws(TokenCreationException::class)
    fun authenticateAndGenerateToken(loginRequest: LoginRequest): LoginResponse {
        log.info { "Authenticating user with email: ${loginRequest.email} and password: ${loginRequest.password}" }
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
        val principal: AppUserDetails = authentication.principal as AppUserDetails

        val token = createToken(principal) ?: throw TokenCreationException("Failed to generate JWT token") //TODO : NO IDEA WHERE TO HANDLE IT
        val email = principal.username!! // this cant fail because the dto will be validated on entering the controller
        userRepository.findByEmail(email)

        return LoginResponse(token) //TODO : implement error handling ( wrong user or wrong password if user exists)
    }

    private fun createToken(user: AppUserDetails): String? {
        val secret = properties.secret
        val algorithm = Algorithm.HMAC256(secret)
        val localDateTime = LocalDateTime.now(clock).atZone(ZoneId.of("UTC"))
        val now = localDateTime.toInstant()
        val expireAt = now.plus(Duration.ofHours(properties.hours))
        val issuer = "Questy"

        return JWT.create()
            .withSubject(user.username)
            .withIssuedAt(now)
            .withExpiresAt(expireAt)
            .withIssuer(issuer)
            .sign(algorithm)
    }
}
