package questy.service

import org.springframework.stereotype.Service
import questy.repository.IssueCommentRepository
import questy.repository.IssueRepository

@Service
class IssueCommentService(
    private val  issueCommentRepository: IssueCommentRepository
){}
