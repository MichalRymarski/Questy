package questy.controller

import org.springframework.web.bind.annotation.RestController
import questy.service.ProjectService

@RestController
class ProjectController(
    private val projectService : ProjectService
){

}
