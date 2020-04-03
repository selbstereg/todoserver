package todoappbackend.todoserver.todolist

import todoappbackend.todoserver.todolist.todo.ToDo
import javax.persistence.*

@Entity
data class ToDoList(val name: String) {

    @OneToMany(cascade = [CascadeType.PERSIST], orphanRemoval = true)
    private val _todos: MutableList<ToDo> = mutableListOf()
    val todos: List<ToDo>
        get() {
            return _todos
        }

    @Id
    @GeneratedValue
    val id: Long? = null

    fun add(toDo: ToDo) {
        _todos.add(toDo)
    }

    fun find(toDoId: Long): ToDo? {
        return _todos.find { it.id == toDoId }
    }

    fun remove(toDo: ToDo): Boolean {
        return _todos.remove(toDo)
    }

}
