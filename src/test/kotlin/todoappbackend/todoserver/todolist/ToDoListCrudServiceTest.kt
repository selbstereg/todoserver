package todoappbackend.todoserver.todolist

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.amshove.kluent.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import todoappbackend.todoserver.errorhandling.EntityNotFoundException
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo
import todoappbackend.todoserver.todolist.todo.ToDo
import todoappbackend.todoserver.todolist.todo.ToDoRepo

class ToDoListCrudServiceTest {

    private val toDoListRepo: ToDoListRepo = mockk()
    private val toDoRepo: ToDoRepo = mockk()
    private lateinit var toDoListCrudService: ToDoListCrudService
    private val expectedToDoList = ToDoList(name = "TestString")

    @BeforeEach
    fun setUp() {
        toDoListCrudService = ToDoListCrudService(toDoListRepo, toDoRepo)
    }

    @Test
    fun `should get to do list from repo`() {
        val expectedToDoLists = listOf(expectedToDoList)
        every { toDoListRepo.findAll() } returns expectedToDoLists

        val toDoLists = toDoListCrudService.getToDoLists()

        toDoLists `should be` expectedToDoLists
    }

    @Test
    fun `should create to do list with name`() {
        val slot = slot<ToDoList>()
        every { toDoListRepo.save(capture(slot)) } returns expectedToDoList

        val createdToDoList = toDoListCrudService.createToDoList("TestString")

        slot.captured.name `should be` "TestString"
        createdToDoList `should be` expectedToDoList
    }

    @Test
    fun `should delegate deletion of to do list to repo`() {
        val idSlot = slot<Long>()
        val toDoListSlot = slot<ToDoList>()
        val toDoListId = 42L
        every { toDoListRepo.findByIdOrNull(capture(idSlot)) } returns expectedToDoList
        every { toDoListRepo.delete(capture(toDoListSlot)) } returns Unit

        val deletedToDoList = toDoListCrudService.deleteToDoList(toDoListId)

        idSlot.captured `should be` toDoListId
        toDoListSlot.captured `should be` expectedToDoList
        deletedToDoList `should be` expectedToDoList
    }

    @Test
    fun `should trow an exception when to do list with given id is not found`() {
        every { toDoListRepo.findByIdOrNull(any()) } returns null

        invoking {
            toDoListCrudService.deleteToDoList(42L)
        } `should throw` EntityNotFoundException::class
    }

    @Test
    fun `should add new ToDo to a ToDoList`() {
        val toDoToAdd = createToDo("TestToDo")
        val savedToDo = createToDo("TestToDo after saving")
        val toDoSlot = slot<ToDo>()
        val toDoListSlot = slot<ToDoList>()
        every { toDoRepo.save(capture(toDoSlot)) } returns savedToDo
        every { toDoListRepo.findByIdOrNull(42L) } returns ToDoList("AddToThisList")
        every { toDoListRepo.save(capture(toDoListSlot)) } returns ToDoList("irrelevant")

        val addedToDo = toDoListCrudService.addToDo(42L, toDoToAdd)

        toDoSlot.captured `should be` toDoToAdd
        toDoListSlot.captured.toDos `should contain` savedToDo
        addedToDo `should be` savedToDo
    }

    @Test
    fun `should throw an exception when trying to add to do but to do list is not found`() {
        every { toDoRepo.save(ofType(ToDo::class)) } returns createToDo("irrelevant to do")
        every { toDoListRepo.findByIdOrNull(any()) } returns null

        invoking {
            toDoListCrudService.addToDo(42L, createToDo("some name"))
        } `should throw` EntityNotFoundException::class
    }
    
    @Test
    fun `should delegate deletion of to do to repo`() {
        val toDoListId = 42L
        val idOfToDoToDelete = 43L
        val toDoList = ToDoList("some name")
        val toDoToDelete = createToDo("some other name", id=idOfToDoToDelete)
        val toDoToKeep = createToDo("another name", id = 44L)
        toDoList.add(toDoToKeep)
        toDoList.add(toDoToDelete)
        val toDoListAfterRemovalSlot = slot<ToDoList>()
        every { toDoListRepo.findByIdOrNull(toDoListId) } returns toDoList
        every { toDoListRepo.save(capture(toDoListAfterRemovalSlot)) } returns toDoList

        val deletedToDo = toDoListCrudService.deleteToDo(toDoListId, idOfToDoToDelete)

        toDoListAfterRemovalSlot.captured.toDos `should not contain` toDoToDelete
        toDoListAfterRemovalSlot.captured.toDos `should contain` toDoToKeep
        deletedToDo `should be equal to` toDoToDelete
    }

    @Test
    fun `should throw an exception when trying to delete a to do but to do list is not found`() {
        every { toDoListRepo.findByIdOrNull(any()) } returns null

        invoking {
            toDoListCrudService.deleteToDo(42L, 43L)
        } `should throw` EntityNotFoundException::class
    }

    @Test
    fun `should throw an exception when trying to delete a to do which is not found`() {
        val emptyToDoList = ToDoList("empty to do list")
        every { toDoListRepo.findByIdOrNull(any()) } returns emptyToDoList

        invoking {
            toDoListCrudService.deleteToDo(42L, 43L)
        } `should throw` EntityNotFoundException::class
    }

    @Test
    fun `should throw an exception when trying to get to dos from list which is not found`() {
        val toDoListId = 42L
        every { toDoListRepo.findByIdOrNull(eq(toDoListId)) } returns null

        invoking {
            toDoListCrudService.getToDos(toDoListId)
        } `should throw` EntityNotFoundException::class
    }

    @Test
    fun `should return to dos of to do list`() {
        val toDoListId = 42L
        val toDo1 = createToDo("to do 1")
        val toDo2 = createToDo("to do 2")
        expectedToDoList.add(toDo1)
        expectedToDoList.add(toDo2)
        every { toDoListRepo.findByIdOrNull(eq(toDoListId)) } returns expectedToDoList

        val toDos = toDoListCrudService.getToDos(toDoListId)

        toDos `should be` expectedToDoList.toDos
    }
}
