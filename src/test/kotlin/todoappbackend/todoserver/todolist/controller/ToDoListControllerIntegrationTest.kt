package todoappbackend.todoserver.todolist.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.clearMocks
import io.mockk.every
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterEach
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
import todoappbackend.todoserver.todolist.ToDoListCrudService


@WebMvcTest(controllers = [ToDoListController::class])
class ToDoListControllerIntegrationTest {

    private lateinit var expectedToDoList: ToDoList
    private lateinit var expectedToDoLists: List<ToDoList>

    // TODO Paul Bauknecht 18 Apr 2020: Fix code duplication in MockMvc tests, e.g. by creating an abstract base test
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoListServiceMock: ToDoListCrudService



    @BeforeEach
    fun setUp() {
        expectedToDoList = ToDoList("some name")
        expectedToDoLists = listOf(expectedToDoList)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(toDoListServiceMock)
    }

    @Test
    fun `should get to do lists from service`() {
        every { toDoListServiceMock.getToDoLists() } returns expectedToDoLists

        val url = "/api/to-do-lists"
        mockMvc.get(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$").isArray
                    jsonPath("$", hasSize<Int>(expectedToDoLists.size))
                    jsonPath("$[0].name", equalTo(expectedToDoList.name))
                }
    }

    @Test
    fun `should delegate creation of to do lists to service`() {
        val toDoListName = "some name"
        every { toDoListServiceMock.createToDoList(eq(toDoListName)) } returns ToDoList(toDoListName)

        val url = "/api/to-do-lists"
        mockMvc.post(url) {
                    content = toDoListName
                }
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(toDoListName))
                }
    }

    @Test
    fun `should delegate deletion of to do list to service`() {
        val toDoListId = 42L
        every { toDoListServiceMock.deleteToDoList(eq(42L)) } returns expectedToDoList

        val url = "/api/to-do-lists/${toDoListId}"
        mockMvc.delete(url)
                .andExpect {
                    status { isOk }
                    jsonPath("$.name", equalTo(expectedToDoList.name))
                }
    }
}
