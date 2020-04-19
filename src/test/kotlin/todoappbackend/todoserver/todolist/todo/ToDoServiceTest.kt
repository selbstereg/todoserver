package todoappbackend.todoserver.todolist.todo

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.invoking
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import todoappbackend.todoserver.errorhandling.EntityNotFoundException
import todoappbackend.todoserver.utils.TestWithMockkMocks
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo

class ToDoServiceTest : TestWithMockkMocks() {
    private val repo: ToDoRepo = mockk()
    private val toDoId = 42L
    private val expectedToDo = createToDo()
    private val toDoService = ToDoService(repo)

    @Test
    fun `should use repo to update priority of to do`() {
        val toDoSlot = slot<ToDo>()

        val priority = 42
        every { repo.findByIdOrNull(eq(toDoId)) } returns expectedToDo
        every { repo.save(capture(toDoSlot)) } returns createToDo()

        toDoService.updatePriority(toDoId, priority)

        toDoSlot.captured `should be` expectedToDo
        toDoSlot.captured.priority `should be` priority
    }

    @Test
    fun `should throw exception when to do to update can't be found`() {
        every { repo.findByIdOrNull(eq(toDoId)) } returns null

        invoking {
            toDoService.updatePriority(toDoId, 42)
        } `should throw` EntityNotFoundException::class
    }
}