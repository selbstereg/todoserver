package todoappbackend.todoserver.todolist

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not contain`
import org.junit.jupiter.api.Test


class ToDoListIntegrationTest {
    @Test
    fun `should allow finding ToDos while ToDos are mutated`() {
        val toDoList = ToDoList("Test")
        val toDo = ToDo("aToDo")
        toDoList.add(toDo)
        toDo.id = 123L
        toDoList.todos `should contain` toDo
    }

    @Test
    fun `should allow adding distinct ToDos with the same name`() {
        val toDoList = ToDoList("Test")
        val toDo = ToDo("aToDo")
        val toDoWithSameName = ToDo("aToDo")

        toDoList.add(toDo)
        toDoList.add(toDoWithSameName)

        toDoList.todos.size `should be equal to` 2
    }

    @Test
    fun `should consider ids when deciding if ToDo is contained in ToDoList`() {
        val toDoList = ToDoList("Test")
        val toDo = ToDo("aToDo")
        toDo.id = 1L
        val toDoWithSameName = ToDo("aToDo")
        toDoWithSameName.id = 2L

        toDoList.add(toDo)

        toDoList.todos `should not contain` toDoWithSameName
    }
}