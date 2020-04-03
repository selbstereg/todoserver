package todoappbackend.todoserver.todolist.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.ToDoListCrudService
import todoappbackend.todoserver.todolist.todo.ToDo


class ToDoControllerTest {

    private lateinit var toDoListController: ToDoController

    private lateinit var expectedToDoList: ToDoList
    private lateinit var expectedToDoLists: List<ToDoList>
    private var toDoListServiceMock: ToDoListCrudService = mockk()

    @BeforeEach
    fun setUp() {
        expectedToDoList = ToDoList("some name")
        expectedToDoLists = listOf(expectedToDoList)
        toDoListController = ToDoController(toDoListServiceMock)
    }

    @Test
    fun `Should delegate adding ToDos to ToDoListService`() {
        val toDoToCreate = ToDo("TestToDo")
        every { toDoListServiceMock.addToDo(eq(42L), eq(toDoToCreate)) } returns toDoToCreate

        val createdToDo = toDoListController.addToDo(42L, toDoToCreate)

        verify(exactly = 1) { toDoListServiceMock.addToDo(toDoListId = 42L, toDo = toDoToCreate) }
        createdToDo `should be` toDoToCreate
    }

}