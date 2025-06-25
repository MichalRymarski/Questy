package questy.service

import org.springframework.stereotype.Service
import questy.repository.AppUserRepository

@Service
class AppUserService(
    private val appUserRepository: AppUserRepository,
) {
    fun getAllUsers(): List<String> {
        return listOf("User1", "User2", "User3")
    }

}
