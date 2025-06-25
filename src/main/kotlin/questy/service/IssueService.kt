package questy.service

import org.springframework.stereotype.Service
import questy.repository.IssueRepository

@Service
class IssueService(
    private val issueRepository : IssueRepository
) {
}
