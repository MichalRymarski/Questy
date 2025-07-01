package questy.exception.type

class RegistrationValidationException(
    val errors: Map<String, String>
) : RuntimeException("Registration failed due to validation errors")