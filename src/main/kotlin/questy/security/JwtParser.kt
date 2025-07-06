package questy.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import questy.exception.type.auth.InvalidJwtTokenException

@Component
class JwtParser(
    private val properties: JwtConfigProperties,
    private val userDetailsService: UserDetailsService,
) {
    fun parseToken(token: String): UsernamePasswordAuthenticationToken {
        try {
            val secretKey: String = properties.secret
            val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
            val verifier: JWTVerifier = JWT.require(algorithm).build()
            val jwt: DecodedJWT = verifier.verify(token.substring(7))

            val userDetails = userDetailsService.loadUserByUsername(jwt.subject) as AppUserDetails
            return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        } catch (ex: JWTVerificationException) {
            throw InvalidJwtTokenException("Invalid JWT token: ${ex.message}")
        }
    }
}