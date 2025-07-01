# Exception Handling in Spring Boot with Kotlin

Based on your project structure, I'll explain how to implement proper exception handling in your Spring Boot Kotlin application.

## Where to Put Exception Definitions

You already have an `exception` directory at `/src/main/kotlin/questy/exception/` which is the perfect place to define your custom exceptions. Here's
how you should organize it:

### 1. Custom Exception Classes

Create specific exception classes in the `exception` package:

```kotlin
package questy.exception

// Base exception class
abstract class QuestyException(
    override val message: String,
    val errorCode: String
) : RuntimeException(message)

// Specific exceptions
class ResourceNotFoundException(
    resource: String,
    id: Any
) : QuestyException(
    "Resource $resource with id $id not found",
    "RESOURCE_NOT_FOUND"
)

class UnauthorizedException(
    override val message: String = "Unauthorized access"
) : QuestyException(message, "UNAUTHORIZED")

class ValidationException(
    override val message: String,
    val fieldErrors: Map<String, String> = emptyMap()
) : QuestyException(message, "VALIDATION_ERROR")

// Add more specific exceptions as needed
```

## Where to Put Exception Handlers

### 2. Global Exception Handler

Create a global exception handler class in the `exception` package:

```kotlin
package questy.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    // Structure for error responses
    data class ErrorResponse(
        val timestamp: LocalDateTime = LocalDateTime.now(),
        val status: Int,
        val error: String,
        val message: String,
        val path: String,
        val errorCode: String? = null,
        val fieldErrors: Map<String, String>? = null
    )

    // Handle custom base exception
    @ExceptionHandler(QuestyException::class)
    fun handleQuestyException(
        ex: QuestyException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val status = when (ex) {
            is ResourceNotFoundException -> HttpStatus.NOT_FOUND
            is UnauthorizedException -> HttpStatus.UNAUTHORIZED
            is ValidationException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val errorResponse = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message,
            path = request.getDescription(false).substring(4),
            errorCode = ex.errorCode,
            fieldErrors = (ex as? ValidationException)?.fieldErrors
        )

        return ResponseEntity(errorResponse, status)
    }

    // Handle validation exceptions from @Valid annotations
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val fieldErrors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Validation failed",
            path = request.getDescription(false).substring(4),
            errorCode = "VALIDATION_ERROR",
            fieldErrors = fieldErrors
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = ex.message ?: "Unexpected error occurred",
            path = request.getDescription(false).substring(4)
        )

        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
```

## Using Exceptions in Your Services

In your service classes, throw appropriate exceptions when needed:

```kotlin
@Service
class ProjectService(
    private val projectRepository: ProjectRepository
) {
    fun getProjectById(id: Long): Project {
        return projectRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Project", id)
        }
    }

    // Other methods...
}
```

## Best Practices

1. **Be Specific**: Create specific exception types for different error scenarios
2. **Consistent Response Format**: Maintain a consistent error response structure
3. **Proper HTTP Status Codes**: Use appropriate HTTP status codes for different exceptions
4. **Logging**: Add logging to your exception handler for monitoring and debugging
5. **Security**: Be careful not to expose sensitive information in error messages

## Additional Features

You can enhance your exception handling with:

1. **Internationalization**: Add support for localized error messages
2. **Request Tracing**: Include request IDs in error responses for better debugging
3. **Metrics**: Track exception occurrences for monitoring

This approach provides a clean, maintainable way to handle exceptions in your Spring Boot Kotlin application while following best practices.