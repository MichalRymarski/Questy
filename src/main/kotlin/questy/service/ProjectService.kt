package questy.service

import org.springframework.stereotype.Service
import questy.data.entity.Project
import questy.dto.project.ProjectCreationRequest
import questy.exception.type.auth.NullUserInPrincipalException
import questy.exception.type.project.NonUniqueProjectNameException
import questy.logger
import questy.repository.ProjectRepository
import questy.security.AppUserDetails
import questy.util.mapper.ProjectMapper

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val projectMapper: ProjectMapper
) {
    private val log = logger()

    fun createNewProject(
        projectCreationRequest: ProjectCreationRequest,
        currentAuthenticatedUser: AppUserDetails
    ): Project {
        val currentUserId =
            currentAuthenticatedUser.getId() ?: throw NullUserInPrincipalException("Current user id is null while creating a new project")
        val newProject = projectMapper.fromProjectCreationRequestToProject(projectCreationRequest, currentUserId)
        if (projectRepository.existsByName(newProject.name)) {
            throw NonUniqueProjectNameException("Project with name ${newProject.name} already exists")
        }

        return projectRepository.save(newProject)
    }

}
