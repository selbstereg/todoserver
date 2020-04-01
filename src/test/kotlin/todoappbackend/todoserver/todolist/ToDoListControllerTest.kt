package todoappbackend.todoserver.todolist

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.ToDoListCrudService
import todoappbackend.todoserver.todolist.ToDoListController

class ToDoListControllerTest {
    @Test
    fun shouldGetToDoListsFromService() {
        val expectedToDoLists = listOf(ToDoList("some name"))
        val toDoListServiceMock: ToDoListCrudService = mockk()
        every { toDoListServiceMock.getToDoLists() } returns expectedToDoLists
        val toDoListController = ToDoListController(toDoListServiceMock)

        val toDoLists = toDoListController.getToDoLists()

        assertThat(toDoLists).isEqualTo(expectedToDoLists)
    }
}

