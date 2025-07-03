package questy.exception.type.auth

class RegistrationValidationException(
    val errors: Map<String, String>
) : RuntimeException("Registration failed due to validation errors")