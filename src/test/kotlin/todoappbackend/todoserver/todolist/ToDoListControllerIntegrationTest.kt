package todoappbackend.todoserver.todolist

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import todoappbackend.todoserver.exceptions.EntityNotFoundException

@WebMvcTest(controllers = [ToDoListController::class])
class ToDoListControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoCrudServiceMock: ToDoListCrudService

    @Test
    fun `Should return 404 response when EntityNotFoundException is thrown`() {
        val id = 42L
        every { toDoCrudServiceMock.deleteToDoList(any()) } throws EntityNotFoundException(id)

        mockMvc.delete("/api/to-do-lists/$id").andExpect {
            status { isNotFound }
            content { json("\"Entity with id $id not found\"") }
        }
    }
}
