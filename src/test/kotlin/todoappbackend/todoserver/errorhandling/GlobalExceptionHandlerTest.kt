package todoappbackend.todoserver.errorhandling

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [ExceptionTestController::class])
class GlobalExceptionHandlerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return 404 when handling EntityNotFoundException`() {
        mockMvc.get("/test-endpoint").andExpect {
            status { isNotFound }
            content { json("\"Entity with id 42 not found\"") }
        }
    }

}