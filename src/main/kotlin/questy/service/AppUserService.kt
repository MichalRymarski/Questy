package questy.service

import org.springframework.stereotype.Service
import questy.dto.auth.RegisterRequest
import questy.exception.type.RegistrationValidationException
import questy.repository.AppUserRepository
import questy.util.mapper.RegisterMapper

@Service
class AppUserService(
    private val registerMapper: RegisterMapper,
    private val appUserRepository: AppUserRepository,
) {
    fun getAllUsers(): List<String> {
        return listOf("User1", "User2", "User3")
    }

    fun registerUser(registerRequest: RegisterRequest) {
        val errors = mutableMapOf<String, String>()

        if (appUserRepository.existsByUsername(registerRequest.username)) {
            errors["username"] = "Username is already taken"
        }
        if (appUserRepository.existsByEmail(registerRequest.email)) {
            errors["email"] = "Email is already registered"
        }
        if (errors.isNotEmpty()) {
            throw RegistrationValidationException(errors)
        }

        val newUser = registerMapper.fromRequestToAppUser(registerRequest)

        appUserRepository.save(newUser)
    }
}
