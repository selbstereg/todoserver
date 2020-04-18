package todoappbackend.todoserver.todolist

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not contain`
import org.junit.jupiter.api.Test
import todoappbackend.todoserver.testutils.ToDoBuilder.Companion.createToDo


class ToDoListIntegrationTest {

    // INTERESTING: If you put objects into a set, you should not mutate them
    // afterwards. If the mutation affects the equals (or hash code?) method,
    // they can no longer be found.
    // This may yield funny behavior, when using entities, which get their
    // id assigned, when first saved to the data base.
    // This can be tackled, by either not putting the id into the primary constructor,
    // or by not using a Set.

    @Test
    fun `should allow finding ToDos while ToDos are mutated`() {
        val toDoList = ToDoList("Test")
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
    fun `should allow adding distinct ToDos with the same name`() {
        val toDoList = ToDoList("Test")
        val toDo = createToDo("aToDo")
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
    fun `should consider ids when deciding if ToDo is contained in ToDoList`() {
        val toDoList = ToDoList("Test")
        val toDo = createToDo("aToDo")
        toDo.id = 1L
        val toDoWithSameName = createToDo("aToDo")
        toDoWithSameName.id = 2L

        toDoList.add(toDo)

        toDoList.toDos `should not contain` toDoWithSameName
    }
}
