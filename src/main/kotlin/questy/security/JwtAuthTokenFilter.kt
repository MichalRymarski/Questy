package questy.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import questy.exception.type.auth.InvalidJwtTokenException
import questy.exception.type.auth.MissingAuthorizationHeaderException
import questy.logger
import java.io.IOException


@Component
class JwtAuthTokenFilter(
    private val jwtParser: JwtParser,
) : OncePerRequestFilter() {
    private val log = logger()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorization = request.getHeader("Authorization") ?: throw MissingAuthorizationHeaderException("Authorization header is missing")
            val usernamePasswordAuthenticationToken = jwtParser.parseToken(authorization)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            filterChain.doFilter(request, response)
        } catch (ex: MissingAuthorizationHeaderException) {
            filterChain.doFilter(request, response) //Pass this further it will be dealt with
        } catch (ex: InvalidJwtTokenException) {
            handleJwtError(response, ex) //IMPORTANT: has to be handled here because we are in a servlet not the controller layer
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
}