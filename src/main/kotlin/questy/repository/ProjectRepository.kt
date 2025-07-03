package questy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import questy.data.entity.Project


@Repository
interface ProjectRepository : JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    fun existsByName(name: String): Boolean
}