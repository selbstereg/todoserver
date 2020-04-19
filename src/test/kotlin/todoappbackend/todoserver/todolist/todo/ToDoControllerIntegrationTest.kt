package todoappbackend.todoserver.todolist.todo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put
import todoappbackend.todoserver.utils.TestWithMockkMocks

@WebMvcTest(controllers = [ToDoController::class])
class ToDoControllerIntegrationTest : TestWithMockkMocks() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoService: ToDoService

    @Test
    fun `post of altered to do priority should return to do as provided by service`() {
        val toDoId = 42L
        val priority = 4
        every { toDoService.updatePriority(eq(toDoId), eq(priority)) } returns Unit

        val url = "/api/to-dos/$toDoId/priority"
        mockMvc.put(url) {
                    content = priority
                    contentType = MediaType.APPLICATION_JSON
                }
                .andExpect {
                    status { isOk }
                }
    }
}