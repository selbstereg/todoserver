package todoappbackend.todoserver.todolist

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import todoappbackend.todoserver.errorhandling.EntityNotFoundException


class ToDoListServiceTest {
    private val toDoListRepo: ToDoListRepo = mockk()
    private lateinit var toDoListService: ToDoListService
    private val toDoListId = 42L
    private val expectedToDoList = ToDoList("some name")

    @BeforeEach
    fun setUp() {
        toDoListService = ToDoListService(toDoListRepo)
    }

    @Test
    fun `should create to do list with name`() {
        val name = "to do list name"
        val slot = slot<ToDoList>()
        every { toDoListRepo.save(capture(slot)) } returns expectedToDoList

        val createdToDoList = toDoListService.createToDoList(name)

        slot.captured.name `should be` name
        createdToDoList `should be` expectedToDoList
    }

    @Test
    fun `should get to do lists from repo`() {
        val expectedToDoLists = listOf(expectedToDoList)
        every { toDoListRepo.findAll() } returns expectedToDoLists

        val toDoLists = toDoListService.getToDoLists()

        toDoLists `should be` expectedToDoLists
    }

    @Test
    fun `should get to do list from repo`() {
        every { toDoListRepo.findByIdOrNull(eq(toDoListId)) } returns expectedToDoList

        val toDoList = toDoListService.getToDoList(toDoListId)

        toDoList `should be` expectedToDoList
    }

    @Test
    fun `should throw an exception when to do list with given id is not found`() {
        every { toDoListRepo.findByIdOrNull(any()) } returns null

        invoking {
            toDoListService.getToDoList(42L)
        } `should throw` EntityNotFoundException::class
    }

    @Test
    fun `should delegate saving of to do list to repo`() {
        val toDoListToSave = ToDoList("some name")
        every { toDoListRepo.save(eq(toDoListToSave)) } returns expectedToDoList

        val toDoList = toDoListService.save(expectedToDoList)

        toDoList `should be` expectedToDoList
    }

    @Test
    fun `should delegate deletion of to do list to repo`() {
        every { toDoListRepo.findByIdOrNull(eq(toDoListId)) } returns expectedToDoList
        every { toDoListRepo.delete(eq(expectedToDoList)) } returns Unit

        val deletedToDoList = toDoListService.deleteToDoList(toDoListId)

        deletedToDoList `should be` expectedToDoList
    }

    @Test
    fun `should throw an exception when to do list to delete is not found`() {
        every { toDoListRepo.findByIdOrNull(any()) } returns null

        invoking {
            toDoListService.deleteToDoList(42L)
        } `should throw` EntityNotFoundException::class
    }

}