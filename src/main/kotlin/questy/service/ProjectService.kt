package questy.service

import org.springframework.stereotype.Service
import questy.repository.ProjectRepository

@Service
class ProjectService(
    private val projectRepository: ProjectRepository
) {

}
