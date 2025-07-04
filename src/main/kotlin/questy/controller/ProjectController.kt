package questy.controller

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import questy.dto.project.ProjectCreationRequest
import questy.logger
import questy.security.AppUserDetails
import questy.service.ProjectService

@RestController
@RequestMapping("/project")
class ProjectController(
    private val projectService: ProjectService
) {
    private val log = logger()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewProject(
        @RequestBody
        projectCreationRequest: ProjectCreationRequest,
        @AuthenticationPrincipal
        currentAuthenticatedUser: AppUserDetails
    ) {
        log.info {
            """
            currentAuth : $currentAuthenticatedUser,
            project: $projectCreationRequest
        """.trimIndent()
        }
        projectService.createNewProject(projectCreationRequest, currentAuthenticatedUser)
    }
}
