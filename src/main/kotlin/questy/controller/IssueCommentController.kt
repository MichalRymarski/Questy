package questy.controller

import org.springframework.web.bind.annotation.RestController
import questy.service.IssueCommentService

@RestController
class IssueCommentController(
    private val issueCommentService: IssueCommentService
) {

}
