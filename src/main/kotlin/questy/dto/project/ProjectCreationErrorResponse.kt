package questy.dto.project

data class ProjectCreationErrorResponse(
    val projectNameError: String? = null,
    val serverError: String? = null
)
