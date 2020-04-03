package todoappbackend.todoserver.todolist

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post
import todoappbackend.todoserver.exceptions.EntityNotFoundException
import todoappbackend.todoserver.todolist.todo.ToDo

@WebMvcTest(controllers = [ToDoListController::class])
class ToDoListControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

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

    @Test
    fun `Should return the created ToDo when adding a ToDo to a list`() {
        val toDoToCreate = ToDo("TestToDo")
        every { toDoCrudServiceMock.addToDo(any(), any()) } returns toDoToCreate

        val url = "/api/to-do-lists/${42L}/to-dos"
        post(url, toDoToCreate)
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo("TestToDo"))
                }
    }

    @Test
    fun `should return deleted to do, when removing to do from a list`() {
        val toDoListId = 42L
        val toDoId = 43L
        val name = "to be removed"
        val removedToDo = ToDo(name)
        every { toDoCrudServiceMock.deleteToDo(eq(toDoListId), eq(toDoId)) } returns removedToDo

        val url = "/api/to-do-lists/${toDoListId}/to-dos/${toDoId}"
        mockMvc.delete(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(name))
                }
    }

    private fun post(url: String, body: ToDo): ResultActionsDsl {
        return mockMvc.post(url) {
            content = mapper.writeValueAsString(body)
            contentType = MediaType.APPLICATION_JSON
        }
    }


}
