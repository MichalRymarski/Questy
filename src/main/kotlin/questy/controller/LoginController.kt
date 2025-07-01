package questy.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import questy.dto.auth.LoginRequest
import questy.dto.auth.LoginResponse
import questy.logger
import questy.service.LoginService

@RestController
@RequestMapping("/login")
class LoginController(
    private val loginService: LoginService,
) {
    private val logger = logger()

    @PostMapping
    fun login(
        @RequestBody
        loginRequest: LoginRequest
    ): ResponseEntity<LoginResponse> {
        logger.info { "Login endpoint hit" }
        val token = loginService.login(loginRequest)

        return ResponseEntity.ok(token)
    }
}
