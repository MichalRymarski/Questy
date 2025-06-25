package questy.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerResponse.async
import questy.logger
import questy.service.AppUserService

@RestController
@RequestMapping("/user")
class AppUserController(
    private val appUserService: AppUserService,
) {
    private val logger = logger()

    @GetMapping("/all")
    fun getAllUsers(): ResponseEntity<List<String>> {
        val users = appUserService.getAllUsers()
        logger.info { "Fetched all users: $users" }
        return ResponseEntity(users, HttpStatus.OK)
    }
}
