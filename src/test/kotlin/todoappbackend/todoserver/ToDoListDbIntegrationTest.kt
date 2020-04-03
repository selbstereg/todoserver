package todoappbackend.todoserver

import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.todo.ToDo

@DataJpaTest
class ToDoListDbIntegrationTest {

    @Autowired
    private lateinit var testEntityManager: TestEntityManager

    // Tests that only save entities are there, to ensure, that
    // the @Entity annotation is present and generation of the
    // ids works.
    // If the schema were not auto generated, they might also
    // check for compliance of the Entity classes.

    @Test
    fun `should save a to do to the database and set its id`() {
        val savedToDo = testEntityManager.persistFlushFind(ToDo("some name"))
        savedToDo.id `should not be` null
    }

    @Test
    fun `should save a to do list to the database and set its id`() {
        val savedToDoList = testEntityManager.persistFlushFind(ToDoList("some name"))
        savedToDoList.id `should not be` null
    }
}
