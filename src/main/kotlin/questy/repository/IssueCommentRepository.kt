package questy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import questy.data.entity.IssueComment

@Repository
interface IssueCommentRepository: JpaRepository<IssueComment, Long> , JpaSpecificationExecutor<IssueComment> {
}