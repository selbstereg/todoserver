package todoappbackend.todoserver.testutils

import todoappbackend.todoserver.todolist.todo.ToDo

class ToDoBuilder {
    companion object {
        fun createToDo(name: String): ToDo {
            return ToDo(name)
        }
    }
}