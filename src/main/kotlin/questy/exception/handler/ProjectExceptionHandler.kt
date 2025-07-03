package questy.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import questy.controller.ProjectController
import questy.dto.project.ProjectCreationErrorResponse
import questy.exception.type.auth.NullUserInPrincipalException
import questy.exception.type.project.NonUniqueProjectNameException

@ControllerAdvice(assignableTypes = [ProjectController::class])
class ProjectExceptionHandler {

    @ExceptionHandler
    fun handleNonUniqueProjectNameException(ex: NonUniqueProjectNameException): ResponseEntity<ProjectCreationErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ProjectCreationErrorResponse(projectNameError = ex.message!!))
    }

    @ExceptionHandler
    fun handleNullUserInPrincipalExceptionDuringCreation(ex: NullUserInPrincipalException): ResponseEntity<ProjectCreationErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ProjectCreationErrorResponse(serverError = ex.message!!))
    }
}