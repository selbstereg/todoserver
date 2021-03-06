package todoappbackend.todoserver.todolist.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.URI_PATH_TO_DO_LISTS
import todoappbackend.todoserver.todolist.service.ToDoListService
import todoappbackend.todoserver.utils.TestWithMockkMocks


@WebMvcTest(controllers = [ToDoListController::class])
class ToDoListControllerIntegrationTest : TestWithMockkMocks() {

    private lateinit var expectedToDoList: ToDoList
    private lateinit var expectedToDoLists: List<ToDoList>

    // TODO Paul Bauknecht 18 Apr 2020: Fix code duplication in MockMvc tests, e.g. by creating an abstract base test
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoListServiceMock: ToDoListService

    @BeforeEach
    fun setUp() {
        expectedToDoList = ToDoList("some name")
        expectedToDoLists = listOf(expectedToDoList)
    }

    @Test
    fun `should return to do lists provided by service`() {
        every { toDoListServiceMock.getToDoLists() } returns expectedToDoLists

        val url = URI_PATH_TO_DO_LISTS
        mockMvc.get(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$").isArray
                    jsonPath("$", hasSize<Int>(expectedToDoLists.size))
                    jsonPath("$[0].name", equalTo(expectedToDoList.name))
                }
    }

    // TODO Paul Bauknecht 19 Apr 2020: Many test names state, what the expected behavior is. They should also state, what the tested method is
    @Test
    fun `should return created to do list`() {
        val toDoListName = "some name"
        every { toDoListServiceMock.createToDoList(eq(toDoListName)) } returns ToDoList(toDoListName)

        val url = URI_PATH_TO_DO_LISTS
        mockMvc.post(url) {
                    content = toDoListName
                }
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(toDoListName))
                }
    }

    @Test
    fun `should return deleted to do list`() {
        val toDoListId = 42L
        every { toDoListServiceMock.deleteToDoList(eq(42L)) } returns expectedToDoList

        val url = "$URI_PATH_TO_DO_LISTS/${toDoListId}"
        mockMvc.delete(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(expectedToDoList.name))
                }
    }
}
