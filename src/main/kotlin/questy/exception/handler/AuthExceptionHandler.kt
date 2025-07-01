package questy.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import questy.controller.LoginController
import questy.controller.RegisterController
import questy.dto.auth.LoginErrorResponse
import questy.dto.auth.RegisterErrorResponse
import questy.exception.type.RegistrationValidationException
import questy.exception.type.TokenCreationException
import questy.logger

@ControllerAdvice(assignableTypes = [LoginController::class, RegisterController::class])
class AuthExceptionHandler {
    val log = logger()

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleEmailNotFound(ex: UsernameNotFoundException): ResponseEntity<LoginErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(LoginErrorResponse(emailError = ex.message))
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleWrongPassword(ex: BadCredentialsException): ResponseEntity<LoginErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(LoginErrorResponse(passwordError = "Invalid password"))
    }

    @ExceptionHandler(TokenCreationException::class)
    fun handleTokenCreationFailure(ex: TokenCreationException): ResponseEntity<LoginErrorResponse> {
        log.info { "FAILED TO GENERATE TOKEN" }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(LoginErrorResponse(serverError = "Internal server error, please try again"))
    }

    @ExceptionHandler(RegistrationValidationException::class)
    fun handleRegistrationValidationException(ex: RegistrationValidationException): ResponseEntity<RegisterErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                RegisterErrorResponse(
                    usernameError = ex.errors["username"],
                    emailError = ex.errors["email"],
                )
            )
    }
}