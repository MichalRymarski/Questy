package questy.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import questy.security.JwtParser
import questy.service.AppUserService

//TODO: DELETE AFTER IMPLEMENTING BUSINESS LOGIC
@WebMvcTest(AppUserController::class)
@ImportAutoConfiguration(classes = [JacksonAutoConfiguration::class])
class AppUserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var appUserServiceMvc: AppUserService

    @MockitoBean
    private lateinit var jwtParser: JwtParser

    private val appUserService = mock<AppUserService>()
    private val controller = AppUserController(appUserService)

    @Test
    fun `should return a list of users`() {

        //Arrange
        val mockUsers = listOf("User1", "User2", "User3")
        whenever(appUserService.getAllUsers()).doReturn(mockUsers)

        //Act
        val response = controller.getAllUsers()

        //Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(mockUsers, response.body)

        //Verify interaction
        verify(appUserService).getAllUsers()
        verifyNoMoreInteractions(appUserService)
    }

    @Test
    @WithMockUser(username = "user")
    fun `mvc test should return list of users`() {
        //Arrange
        val mockUsers = listOf("User1", "User2", "User3")
        whenever(appUserServiceMvc.getAllUsers()).doReturn(mockUsers)

        //Act & Assert
        mockMvc.get("/user") {
            header("Authorization", "Bearer mock-jwt-token")
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0]") { value("User1") }
            }
    }
}