package todoappbackend.todoserver.todolist

import org.amshove.kluent.`should contain`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class ToDoListDbIntegrationTest {

    @Autowired
    private lateinit var repo: ToDoListRepo

    @Test
    fun `Should save a new ToDo when adding to an existing ToDo list`() {
        // TODO: Vielleicht darf man manager und repo nicht mischen, wegen hibernate transactions, lifecycle, ...
        val toDoList = ToDoList("TestList")
        repo.saveAndFlush(toDoList)

        val toDoToAdd = ToDo("TestToDo")
        toDoList.add(toDoToAdd)
        repo.saveAndFlush(toDoList)

        val retrievedToDoList = repo.findByIdOrNull(toDoList.id ?: Assertions.fail("Id was not set when persisting!"))
        (retrievedToDoList ?: Assertions.fail("ToDoList was not found after persisting!")).todos `should contain` toDoToAdd
    }

}
