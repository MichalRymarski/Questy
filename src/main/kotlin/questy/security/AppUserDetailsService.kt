package questy.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import questy.logger
import questy.repository.AppUserRepository


class AppUserDetailsService(
    private val userRepo: AppUserRepository,
) : UserDetailsService {
    private val log = logger()

    /*
    In this system username = email, because login is done with email and password
    * */
    override fun loadUserByUsername(email: String?): UserDetails? {
        log.info { "Loading user $email for user details" }
        if (email == null) {
            log.error { "EMAIL WAS NULL HERE FOR SOME REASON" }
        }
        val appUser = userRepo.findByEmail(email!!) ?: throw UsernameNotFoundException("User not found with email $email")

        return getUser(appUser.username, appUser.password)
    }

    private fun getUser(
        username: String?,
        password: String?
    ) = User(username, password, emptyList()) //TODO : read up upon this
}