package todoappbackend.todoserver.todolist.todo

import org.amshove.kluent.`should be greater than`
import org.amshove.kluent.`should be less than`
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo

class ToDoTest {
    @Test
    fun `should be greater than to do with lower priority`() {
        val toDo1 = createToDo(priority = 1)
        val toDo2 = createToDo(priority = 2)

        toDo2.compareTo(toDo1) `should be greater than` 0
    }

    @Test
    fun `should compare equal to to do with same priority`() {
        val toDo1 = createToDo(priority = 1)
        val toDo2 = createToDo(priority = 1)

        toDo2.compareTo(toDo1) `should be` 0
    }

    @Test
    fun `should be smaller than to do with higher priority`() {
        val toDo1 = createToDo(priority = 2)
        val toDo2 = createToDo(priority = 1)

        toDo2.compareTo(toDo1) `should be less than` 0
    }
}