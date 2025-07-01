package questy.service

import org.springframework.stereotype.Service
import questy.logger
import questy.repository.AppUserRepository


@Service
class RegisterService(
    private val userRepository: AppUserRepository
) {
    val log = logger()

    fun register() {
        log.info { "Registering a user" }
    }
}
