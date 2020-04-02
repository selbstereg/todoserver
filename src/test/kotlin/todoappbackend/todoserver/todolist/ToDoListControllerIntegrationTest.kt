package todoappbackend.todoserver.todolist

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [ToDoListController::class])
class ToDoListControllerIntegrationTest {

    val expectedToDoList: ToDoList = ToDoList("some name")

    @Autowired
    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper

    @MockkBean
    private lateinit var toDoListCrudService: ToDoListCrudService

    @Test
    fun `Should return 404 response when EntityNotFoundException is thrown`() {
        val id = 42L
        every { toDoListCrudService.deleteToDoList(any()) } throws EntityNotFoundException(id)

        mockMvc.delete("/api/to-do-lists/$id").andExpect {
            status { isNotFound }
            content { json("\"Entity with id $id not found\"") }
        }
    }

    @Test
    fun `Should delegate adding ToDos to ToDoListService`() {
        val id = 42L
        every { toDoListCrudService.addToDo(any(), any()) } returns expectedToDoList
        val toDo = ToDo()

        mockMvc.post("/api/to-do-lists/$id") {
            content = mapper.writeValueAsString(toDo)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            //TODO: how do we match for json content? Should we add hamkrest as dependency?
//            jsonPath {("$.id", equals(id))}
        }

        verify(exactly = 1) { toDoListCrudService.addToDo(toToDoListId= id, toDo = toDo) }
    }
}
