package questy.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import questy.dto.auth.RegisterRequest
import questy.logger
import questy.service.AppUserService

@RestController
@RequestMapping("/register")
class RegisterController(
    private val userService: AppUserService
) {
    val log = logger()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
        @RequestBody
        registerRequest: RegisterRequest
    ) {
        userService.registerUser(registerRequest)
    }
}
