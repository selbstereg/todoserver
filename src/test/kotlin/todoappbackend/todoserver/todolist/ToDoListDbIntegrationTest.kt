package todoappbackend.todoserver.todolist

import org.amshove.kluent.`should be null`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo
import todoappbackend.todoserver.todolist.todo.ToDo

@DataJpaTest
class ToDoListDbIntegrationTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    // INTERESTING: The tests that do nothing but save an entity, are there to ensure,
    // that the @Entity annotation is present and generation of the ids works.
    // If the schema were not auto generated, they might also
    // check for compliance of the Entity classes.

    @Test
    fun `should save a to do to the database and set its id`() {
        val savedToDo = entityManager.persistFlushFind(createToDo())
        savedToDo.id `should not be` null
    }

    @Test
    fun `should save a to do list to the database and set its id`() {
        val savedToDoList = entityManager.persistFlushFind(ToDoList("some name"))
        savedToDoList.id `should not be` null
    }

    @Test
    fun `should remove orphaned to dos when removing todo from ToDoList`() {
        val toDoList = ToDoList("some name")
        val toDo = createToDo()
        toDoList.add(toDo)
        val persistedToDo = entityManager.persistAndFlush(toDo)
        entityManager.persistAndFlush(toDoList)

        toDoList.remove(toDo)
        entityManager.persistAndFlush(toDoList)

        val toDoNotToBeFound = entityManager.find(ToDo::class.java, persistedToDo.id)
        toDoNotToBeFound.`should be null`()
    }
}
