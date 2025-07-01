package questy.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "auth.jwt")
data class JwtConfigProperties(
    val secret: String,
    val hours: Long
)
