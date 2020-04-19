package todoappbackend.todoserver.todolist.todo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo

@WebMvcTest(controllers = [ToDoController::class])
class ToDoControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoService: ToDoService

    private var toDoId = 42L
    private var expectedToDo = createToDo("some name")

    @Test
    fun `post of altered to do priority should return to do as provided by service`() {
        val priority = 4
        every { toDoService.updatePriority(eq(toDoId), eq(priority)) } returns Unit

        val url = "/api/to-dos/$toDoId/priority"
        mockMvc.post(url) {
                    content = priority
                    contentType = MediaType.APPLICATION_JSON
                }
                .andExpect {
                    status { isOk }
                }
    }
}