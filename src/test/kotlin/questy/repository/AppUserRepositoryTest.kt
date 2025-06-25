package questy.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional
import questy.TestcontainersConfiguration
import questy.data.entity.AppUser
import questy.toKotlinNullable

@SpringBootTest
@Import(TestcontainersConfiguration::class)
@Transactional
class AppUserRepositoryTest {

    @Autowired
    private lateinit var  appUserRepository: AppUserRepository

    @Test
    fun `should create and retrieve appUser successfully`(){
        //Given
        val appUser = AppUser(
            username = "testuser",
            password = "password123",
            email = "bazinga",
        )

        //When
        val savedUser = appUserRepository.save(appUser)
        val retrievedUser = appUserRepository.findById(1).toKotlinNullable()
        //Then
        assert(retrievedUser != null) { "Retrieved user should not be null" }
        assert(retrievedUser?.username == "testuser") { "Username should match" }
        assert(retrievedUser?.password == "password123") { "Password should match" }
    }
}