package questy.dto.auth

data class LoginErrorResponse(
    val emailError: String? = null,
    val passwordError: String? = null,
    val serverError: String? = null
)
