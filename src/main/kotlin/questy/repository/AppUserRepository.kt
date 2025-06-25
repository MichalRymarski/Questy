package questy.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import questy.data.entity.AppUser

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {

    fun save(appUser: AppUser): AppUser
}