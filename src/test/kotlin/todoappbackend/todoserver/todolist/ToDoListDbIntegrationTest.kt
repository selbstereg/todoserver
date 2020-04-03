package todoappbackend.todoserver.todolist

import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ToDoListDbIntegrationTest {

    @Autowired
    private lateinit var manager: TestEntityManager

    @Test
    fun `Should save a new ToDo when adding to an existing ToDo list`() {
        // TODO: Vielleicht darf man manager und repo nicht mischen, wegen hibernate transactions, lifecycle, ...
        val toDoList = ToDoList("TestList")
        manager.persistAndFlush(toDoList)
        val toDoToAdd = ToDo("TestToDo")
        toDoList.add(toDoToAdd)

        val persistedListWithToDo = manager.persistFlushFind(toDoList)

        persistedListWithToDo.todos `should contain` toDoToAdd
        toDoToAdd.id `should not be`(null)
    }

}
