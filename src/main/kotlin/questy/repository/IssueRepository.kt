package questy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import questy.data.entity.Issue

@Repository
interface IssueRepository : JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {
}