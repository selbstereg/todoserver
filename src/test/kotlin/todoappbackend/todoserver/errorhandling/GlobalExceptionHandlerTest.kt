package todoappbackend.todoserver.errorhandling

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import todoappbackend.todoserver.utils.EXCEPTION_HANDLER_TEST_ENDPOINT
import todoappbackend.todoserver.utils.GlobalExceptionHandlerTestHelperController

@WebMvcTest(controllers = [GlobalExceptionHandlerTestHelperController::class])
class GlobalExceptionHandlerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should handle EntityNotFoundException by responding with 404`() {
        mockMvc.get(EXCEPTION_HANDLER_TEST_ENDPOINT)
                .andExpect {
                    status { isNotFound }
                    content { json("\"Entity with id 42 not found\"") }
                }
    }

}