package todoappbackend.todoserver.todolist.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.amshove.kluent.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.errorhandling.EntityNotFoundException
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.todo.ToDoRepo
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo

class ToDoListItemServiceTest {

    private val toDoListService: ToDoListService = mockk()
    private val toDoRepo: ToDoRepo = mockk()
    private lateinit var toDoListItemService: ToDoListItemService

    private val toDoListId = 42L
    private lateinit var someToDoList: ToDoList

    @BeforeEach
    fun setUp() {
        toDoListItemService = ToDoListItemService(toDoListService, toDoRepo)
        someToDoList = ToDoList("some other name")
    }

    @Test
    fun `should add new to do to a to do list`() {
        val toDoListSlot = slot<ToDoList>()

        val toDoToAdd = createToDo()
        val persistedToDo = createToDo("test to do after saving")
        every { toDoRepo.save(eq(toDoToAdd)) } returns persistedToDo
        every { toDoListService.getToDoList(eq(toDoListId)) } returns someToDoList
        every { toDoListService.save(capture(toDoListSlot)) } returns someToDoList

        val returnedToDo = toDoListItemService.addToDo(toDoListId, toDoToAdd)

        toDoListSlot.captured.toDos `should contain` persistedToDo
        returnedToDo `should be` persistedToDo
    }

    @Test
    fun `should return to dos of to do list`() {
        val toDo1 = createToDo("to do 1")
        val toDo2 = createToDo("to do 2")
        someToDoList.add(toDo1)
        someToDoList.add(toDo2)
        every { toDoListService.getToDoList(eq(toDoListId)) } returns someToDoList

        val toDos = toDoListItemService.getToDos(toDoListId)

        toDos `should contain all` someToDoList.toDos
    }

    @Test
    fun `should update the to do list in order to delete to do`() {
        val idOfToDoToDelete = 43L
        val toDoToDelete = createToDo(id = idOfToDoToDelete)
        val toDoToKeep = createToDo(id = 44L)
        someToDoList.add(toDoToKeep)
        someToDoList.add(toDoToDelete)
        val toDoListAfterRemovalSlot = slot<ToDoList>()
        every { toDoListService.getToDoList(eq(toDoListId)) } returns someToDoList
        every { toDoListService.save(capture(toDoListAfterRemovalSlot)) } returns someToDoList

        val deletedToDo = toDoListItemService.deleteToDo(toDoListId, idOfToDoToDelete)

        toDoListAfterRemovalSlot.captured.toDos `should not contain` toDoToDelete
        toDoListAfterRemovalSlot.captured.toDos `should contain` toDoToKeep
        deletedToDo `should be equal to` toDoToDelete
    }

    @Test
    fun `should throw an exception when trying to delete a to do which is not found`() {
        val emptyToDoList = ToDoList("empty to do list")
        every { toDoListService.getToDoList(any()) } returns emptyToDoList

        invoking {
            toDoListItemService.deleteToDo(42L, 43L)
        } `should throw` EntityNotFoundException::class
    }

}
