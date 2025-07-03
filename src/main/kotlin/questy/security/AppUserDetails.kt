package questy.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import questy.data.entity.AppUser

class AppUserDetails(
    private val appUser: AppUser,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {
    private val accountNonExpired = true
    private val accountNonLocked = true
    private val credentialsNonExpired = true
    private val enabled = true


    override fun getAuthorities(): Collection<GrantedAuthority?>? = authorities

    override fun getPassword(): String? = appUser.password

    /**
     * This is an override, but it actually returns email not username (because email is the verification field in login)
     */
    override fun getUsername(): String? = appUser.email

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}