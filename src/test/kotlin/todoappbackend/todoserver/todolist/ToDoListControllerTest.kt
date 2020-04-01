package todoappbackend.todoserver.todolist

import io.mockk.*
import org.amshove.kluent.`should be`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToDoListControllerTest {
    lateinit var toDoListController: ToDoListController

    lateinit var expectedToDoList: ToDoList
    lateinit var expectedToDoLists: List<ToDoList>

    var toDoListServiceMock: ToDoListCrudService = mockk()


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
    fun shouldGetToDoListsFromService() {
        every { toDoListServiceMock.getToDoLists() } returns expectedToDoLists

        val toDoLists = toDoListController.getToDoLists()

        assertThat(toDoLists).isEqualTo(expectedToDoLists)
    }

    @Test
    fun shouldDelegateCreationOfToDoListToService() {
        val name = "my to do list"
        val slot = slot<String>()
        every { toDoListServiceMock.createToDoList(name = capture(slot)) } returns expectedToDoList

        val toDoList = toDoListController.createToDoList(name)

        verify(exactly = 1) { toDoListServiceMock.createToDoList(name = name) }
        assertThat(toDoList).isEqualTo(expectedToDoList)
    }

    @Test
    fun `should delegate deletion of to do list to service`() {
        val toDoListId = 42L
        val slot = slot<Long>()
        every { toDoListServiceMock.deleteToDoList(id = capture(slot)) } returns expectedToDoList

        val toDoList = toDoListController.deleteToDoList(toDoListId)

        verify(exactly = 1) { toDoListServiceMock.deleteToDoList(id = toDoListId) }
        toDoList `should be` expectedToDoList
    }
}

