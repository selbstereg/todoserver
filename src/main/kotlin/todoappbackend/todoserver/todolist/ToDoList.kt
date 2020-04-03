package todoappbackend.todoserver.todolist

import todoappbackend.todoserver.todolist.todo.ToDo
import javax.persistence.*

@Entity
data class ToDoList(val name: String) {

    @OneToMany
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

}
