package questy.controller

import org.springframework.web.bind.annotation.RestController
import questy.service.IssueService

@RestController
class IssueController(
    private val issueService: IssueService
) {

}
