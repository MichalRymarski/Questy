package questy.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import questy.exception.type.InvalidJwtTokenException
import questy.exception.type.MissingAuthorizationHeaderException
import java.io.IOException


@Component
class JwtAuthTokenFilter(
    private val properties: JwtConfigProperties
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorization = request.getHeader("Authorization") ?: throw MissingAuthorizationHeaderException("Authorization header is missing")
            val usernamePasswordAuthenticationToken = getUserNamePasswordAuthenticationToken(authorization)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        } catch (ex: MissingAuthorizationHeaderException) {
            filterChain.doFilter(request, response) //Pass this further it will be dealt with
        } catch (ex: InvalidJwtTokenException) {
            handleJwtError(response, ex) //IMPORTANT: has to be handled here because we are in a servlet not the controller layer
            return
        }

    }

    private fun handleJwtError(
        response: HttpServletResponse,
        ex: InvalidJwtTokenException
    ) {
        val objectMapper = ObjectMapper()
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"

        val errorResponse = mapOf(
            "error" to "INVALID_TOKEN",
            "message" to ex.message
        )

        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }

    private fun getUserNamePasswordAuthenticationToken(token: String): UsernamePasswordAuthenticationToken {
        try {
            val secretKey: String = properties.secret
            val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
            val verifier: JWTVerifier = JWT.require(algorithm).build()
            val jwt: DecodedJWT = verifier.verify(token.substring(7))
            return UsernamePasswordAuthenticationToken(jwt.subject, null, emptyList())
        } catch (ex: JWTVerificationException) {
            throw InvalidJwtTokenException("Invalid JWT token: ${ex.message}")
        }
    }
}