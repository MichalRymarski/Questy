package questy.util.mapper

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component
import questy.data.entity.AppUser
import questy.data.entity.Project
import questy.dto.project.ProjectCreationRequest

@Component
class ProjectMapper(
    private val entityManager: EntityManager,
) {

    fun fromProjectCreationRequestToProject(
        projectCreationRequest: ProjectCreationRequest,
        currentUserId: Long
    ): Project {
        return Project(
            name = projectCreationRequest.name,
            description = projectCreationRequest.description,
            owner = entityManager.getReference(AppUser::class.java, currentUserId),
        )
    }
}