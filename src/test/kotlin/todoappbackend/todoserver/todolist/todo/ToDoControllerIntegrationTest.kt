package todoappbackend.todoserver.todolist.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put
import todoappbackend.todoserver.todolist.URI_PATH_TO_DOS
import todoappbackend.todoserver.utils.TestWithMockkMocks
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo

@WebMvcTest(controllers = [ToDoController::class])
class ToDoControllerIntegrationTest : TestWithMockkMocks() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoService: ToDoService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `post of changed to do priority should return http ok`() {
        val toDoId = 42L
        val priority = 4
        every { toDoService.updatePriority(eq(toDoId), eq(priority)) } returns Unit

        val url = "$URI_PATH_TO_DOS/$toDoId/priority"
        mockMvc.put(url) {
                    content = priority
                    contentType = MediaType.APPLICATION_JSON
                }
                .andExpect {
                    status { isOk }
                }
    }

    @Test
    fun `put of changed to do should return to do as returned by service`() {
        val toDoToUpdate = createToDo(id = 42)
        val updatedToDo = createToDo(id = 42)
        every { toDoService.update(eq(toDoToUpdate)) } returns updatedToDo

        val url = URI_PATH_TO_DOS
        mockMvc.put(url) {
            content = objectMapper.writeValueAsString(toDoToUpdate)
            contentType = MediaType.APPLICATION_JSON
        }
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(updatedToDo.name))
                }
    }
}
