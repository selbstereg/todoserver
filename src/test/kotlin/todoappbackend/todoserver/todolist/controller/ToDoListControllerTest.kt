package todoappbackend.todoserver.todolist.controller

import io.mockk.*
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.ToDoListCrudService

class ToDoListControllerTest {
    private lateinit var toDoListController: ToDoListController

    private lateinit var expectedToDoList: ToDoList
    private lateinit var expectedToDoLists: List<ToDoList>
    private var toDoListServiceMock: ToDoListCrudService = mockk()

    @BeforeEach
    fun setUp() {
        expectedToDoList = ToDoList("some name")
        expectedToDoLists = listOf(expectedToDoList)
        toDoListController = ToDoListController(toDoListServiceMock)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(toDoListServiceMock)
    }

    @Test
    fun `should get to do lists from service`() {
        every { toDoListServiceMock.getToDoLists() } returns expectedToDoLists

        val toDoLists = toDoListController.getToDoLists()

        toDoLists `should be` expectedToDoLists
    }

    @Test
    fun `should delegate creation of to do lists to service`() {
        val name = "my to do list"
        val slot = slot<String>()
        every { toDoListServiceMock.createToDoList(name = capture(slot)) } returns expectedToDoList

        val toDoList = toDoListController.createToDoList(name)

        verify(exactly = 1) { toDoListServiceMock.createToDoList(name = name) }
        toDoList `should be` expectedToDoList
    }

    @Test
    fun `should delegate deletion of to do list to service`() {
        val toDoListId = 42L
        every { toDoListServiceMock.deleteToDoList(eq(42L)) } returns expectedToDoList

        val toDoList = toDoListController.deleteToDoList(toDoListId)

        verify(exactly = 1) { toDoListServiceMock.deleteToDoList(id = toDoListId) }
        toDoList `should be` expectedToDoList
    }

}
