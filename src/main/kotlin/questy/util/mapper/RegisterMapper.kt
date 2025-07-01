package questy.util.mapper

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import questy.data.entity.AppUser
import questy.dto.auth.RegisterRequest

@Component
class RegisterMapper(
    private val passwordEncoder: PasswordEncoder,
) {
    fun fromRequestToAppUser(dto: RegisterRequest): AppUser {
        return AppUser(
            username = dto.username,
            email = dto.email,
            password = passwordEncoder.encode(dto.password),
        )
    }
}