package todoappbackend.todoserver.todolist

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ToDoListDbIntegrationTest {

    @Autowired
    private lateinit var repo: ToDoListRepo

    @Autowired
    private lateinit var manager: TestEntityManager

    @Test
    fun `Should save a new ToDo when adding to an existing ToDo list`() {
        // TODO: Vielleicht darf man manager und repo nicht mischen, wegen hibernate transactions, lifecycle, ...
        val toDoList = ToDoList("TestList")
        val flusehdToDolist = manager.persistFlushFind(toDoList)

        val toDoToAdd = ToDo("TestToDo")
        flusehdToDolist.add(toDoToAdd)
        repo.save(toDoList);

    }

}
