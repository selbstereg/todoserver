package todoappbackend.todoserver.todolist

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.amshove.kluent.`should be`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ToDoListCrudServiceTest {

    private val repo: ToDoListRepo = mockk()
    private lateinit var toDoListCrudService: ToDoListCrudService
    private val toDoList = ToDoList(name = "TestString")

    @BeforeEach
    fun setUp() {
        toDoListCrudService = ToDoListCrudService(repo)
    }

    @Test
    fun `should Get ToDoList From Repo`() {
        val expectedToDoLists = listOf(toDoList)
        every { repo.findAll() } returns expectedToDoLists

        val toDoLists = toDoListCrudService.getToDoLists()

        assertThat(toDoLists).isEqualTo(expectedToDoLists)
    }

    @Test
    internal fun `should create ToDoList with name`() {
        val slot = slot<ToDoList>()
        every { repo.save(capture(slot)) } returns toDoList

        val createdToDoList = toDoListCrudService.createToDoList("TestString")

        slot.captured.name `should be` "TestString"
        toDoList `should be` createdToDoList
    }
}
