package todoappbackend.todoserver.todolist

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be sorted according to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not contain`
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.todolist.todo.ToDo
import todoappbackend.todoserver.utils.ToDoBuilder.Companion.createToDo


class ToDoListIntegrationTest {

    // INTERESTING: If you put objects into a set, you should not mutate them
    // afterwards. If the mutation affects the equals (or hash code?) method,
    // they can no longer be found.
    // This may yield funny behavior, when using entities, which get their
    // id assigned, when first saved to the data base.
    // This can be tackled, by either not putting the id into the primary constructor,
    // or by not using a Set.

    @Test
    fun `should allow finding ToDos while to dos are mutated`() {
        val toDoList = ToDoList("Test")
        // TODO Paul Bauknecht 19 Apr 2020: Most of the time, when createToDo is used, a name is provided, although it is
        // irrelevant vor the test -> use createToDo() without any arguments.
        val toDo = createToDo("aToDo")
        toDoList.add(toDo)
        toDo.id = 123L
        toDoList.toDos `should contain` toDo
    }

    // INTERESTING: Although there isn't really a requirement for that, we did not
    // want the app to treat to dos with the same name as the same entity, as that
    // did not seem intuitive. As the id may be null in any case, this entails,
    // that the to dos are not stored in a Set.

    @Test
    fun `should allow adding distinct to dos with the same name`() {
        val toDoList = ToDoList("Test")
        val toDo = createToDo()
        val toDoWithSameName = createToDo("aToDo")

        toDoList.add(toDo)
        toDoList.add(toDoWithSameName)

        toDoList.toDos.size `should be equal to` 2
    }

    // INTERESTING: As we allow adding distinct to dos with the same name, it is only consistent,
    // that the id should be considered, when deciding, if a to do is already
    // contained in the to do list. To this end, the id is part of the primary
    // constructor of the to do.

    @Test
    fun `should consider ids when deciding if to do is contained in to do list`() {
        val toDoList = ToDoList("Test")
        val name = "to do name"
        val toDo = createToDo(name)
        toDo.id = 1L
        val toDoWithSameName = createToDo(name)
        toDoWithSameName.id = 2L

        toDoList.add(toDo)

        toDoList.toDos `should not contain` toDoWithSameName
    }

    @Test
    fun `should provide to dos sorted by priority`() {
        val toDoList = ToDoList("Test")

        toDoList.add(createToDo(priority = 3))
        toDoList.add(createToDo(priority = 1))
        toDoList.add(createToDo(priority = -2))
        toDoList.add(createToDo(priority = 7))
        toDoList.add(createToDo(priority = 0))

        val priorityComparator = compareBy<ToDo> { it.priority }
        toDoList.toDos `should be sorted according to` priorityComparator
    }
}
