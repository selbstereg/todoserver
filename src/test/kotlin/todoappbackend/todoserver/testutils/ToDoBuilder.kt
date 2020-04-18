package todoappbackend.todoserver.testutils

import todoappbackend.todoserver.todolist.todo.ToDo

// INTERESTING: Default args make the builder pattern affluent.
// We don't want to provide default args for ToDo's constructor,
// since the clients should be forced to provide this data.
// This is inconvenient, when writing tests, as often some data will be
// irrelevant. In Java, you would create a builder.
// In Kotlin, the following tiny wrapper, which merely defines
// default values for the constructor parameters, is sufficient.

class ToDoBuilder {
    companion object {
        fun createToDo(
                name: String = "name",
                priority: Int = 0,
                id: Long? = null
        ): ToDo {
            return ToDo(name, priority, id)
        }
    }
}