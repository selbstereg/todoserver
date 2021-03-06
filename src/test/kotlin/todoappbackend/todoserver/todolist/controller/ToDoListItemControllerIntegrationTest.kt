package todoappbackend.todoserver.todolist.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import todoappbackend.todoserver.todolist.ENDPOINT_TO_DOS
import todoappbackend.todoserver.todolist.URI_PATH_TO_DO_LISTS
import todoappbackend.todoserver.todolist.service.ToDoListItemService
import todoappbackend.todoserver.utils.TestWithMockkMocks
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo

@WebMvcTest(controllers = [ToDoListItemController::class])
class ToDoListItemControllerIntegrationTest : TestWithMockkMocks() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoListItemService: ToDoListItemService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return to dos provided by service`() {
        val toDoName1 = "to do 1"
        val toDoName2 = "to do 2"
        val toDoListId = 42L
        val expectedToDos = listOf(createToDo(toDoName1), createToDo(toDoName2))
        every { toDoListItemService.getToDos(eq(toDoListId)) } returns expectedToDos

        val url = "$URI_PATH_TO_DO_LISTS/$toDoListId/$ENDPOINT_TO_DOS"
        mockMvc.get(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$").isArray
                    jsonPath("$", Matchers.hasSize<Int>(expectedToDos.size))
                    jsonPath("$[0].name", equalTo(expectedToDos.get(0).name))
                    jsonPath("$[1].name", equalTo(expectedToDos.get(1).name))
                }
    }

    // TODO Paul Bauknecht 16 05 2020: Using jsonPath is queer. Each time, you add a new
    //  property to the serialized class, you would have to extend the test. Try to do sth with jackson
    //  that is more general.
    @Test
    fun `should return the created to do when adding a to do to a list`() {
        val toDoToCreate = createToDo("some name")
        val createdToDo = createToDo("some other name")
        val toDoListId = 42L
        every { toDoListItemService.addToDo(eq(toDoListId), eq(toDoToCreate)) } returns createdToDo

        val url = "$URI_PATH_TO_DO_LISTS/$toDoListId/$ENDPOINT_TO_DOS"
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
        val removedToDo = createToDo(name)
        every { toDoListItemService.deleteToDo(eq(toDoListId), eq(toDoId)) } returns removedToDo

        val url = "$URI_PATH_TO_DO_LISTS/$toDoListId/$ENDPOINT_TO_DOS/$toDoId"
        mockMvc.delete(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(name))
                }
    }

}
