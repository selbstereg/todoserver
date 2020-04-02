package todoappbackend.todoserver.todolist

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class ToDoListCrudServiceTest {

    private val repo: ToDoListRepo = mockk()
    private lateinit var toDoListCrudService: ToDoListCrudService
    private val expectedToDoList = ToDoList(name = "TestString")

    @BeforeEach
    fun setUp() {
        toDoListCrudService = ToDoListCrudService(repo)
    }

    @Test
    fun `should get to do list from repo`() {
        val expectedToDoLists = listOf(expectedToDoList)
        every { repo.findAll() } returns expectedToDoLists

        val toDoLists = toDoListCrudService.getToDoLists()

        toDoLists `should be` expectedToDoLists
    }

    @Test
    fun `should create to do list with name`() {
        val slot = slot<ToDoList>()
        every { repo.save(capture(slot)) } returns expectedToDoList

        val createdToDoList = toDoListCrudService.createToDoList("TestString")

        slot.captured.name `should be` "TestString"
        createdToDoList `should be` expectedToDoList
    }

    @Test
    fun `should delegate deletion of to do list to repo`() {
        val idSlot = slot<Long>()
        val toDoListSlot = slot<ToDoList>()
        val toDoListId = 42L
        every { repo.findByIdOrNull(capture(idSlot)) } returns expectedToDoList
        every { repo.delete(capture(toDoListSlot)) } returns Unit

        val deletedToDoList = toDoListCrudService.deleteToDoList(toDoListId)

        idSlot.captured `should be` toDoListId
        toDoListSlot.captured `should be` expectedToDoList
        deletedToDoList `should be` expectedToDoList
    }

    @Test
    fun `should trow an exception when to do list with given id is not found`() {
        every { repo.findByIdOrNull(any()) } returns null

        invoking { toDoListCrudService.deleteToDoList(42L) } `should throw` EntityNotFoundException::class
    }

    @Test
    fun `should add new ToDo to a ToDoList`() {
        every{repo.findByIdOrNull(42L)} returns ToDoList("AddToThisList")
        val slot = slot<ToDoList>()
        every{repo.save(capture(slot))} returns ToDoList("irrelevant")

        val toDoToAdd = ToDo("TestToDo")
        val addedToDo = toDoListCrudService.addToDo(42L, toDoToAdd)

        slot.captured.todos `should contain` toDoToAdd
        addedToDo `should be` toDoToAdd
    }
}
