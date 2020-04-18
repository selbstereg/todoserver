package todoappbackend.todoserver.todolist.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post
import todoappbackend.todoserver.todolist.ToDoListCrudService
import todoappbackend.todoserver.todolist.todo.ToDo

@WebMvcTest(controllers = [ToDoListItemController::class])
class ToDoListItemControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoListServiceMock: ToDoListCrudService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return the created to do when adding a to do to a list`() {
        val toDoToCreate = ToDo("some name")
        val createdToDo = ToDo("some other name")
        val toDoListId = 42L
        every { toDoListServiceMock.addToDo(eq(toDoListId), eq(toDoToCreate)) } returns createdToDo

        val url = "/api/to-do-lists/$toDoListId/to-dos"
        mockMvc.post(url) {
                    content = objectMapper.writeValueAsString(toDoToCreate)
                    contentType = MediaType.APPLICATION_JSON
                }
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(createdToDo.name))
                }
    }

    @Test
    fun `should return deleted to do, when removing to do from a list`() {
        val toDoListId = 42L
        val toDoId = 43L
        val name = "to be removed"
        val removedToDo = ToDo(name)
        every { toDoListServiceMock.deleteToDo(eq(toDoListId), eq(toDoId)) } returns removedToDo

        val url = "/api/to-do-lists/${toDoListId}/to-dos/${toDoId}"
        mockMvc.delete(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(name))
                }
    }

}