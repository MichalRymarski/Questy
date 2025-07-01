package questy.dto.auth

data class RegisterErrorResponse(
    val usernameError: String? = null,
    val emailError: String? = null,
)
