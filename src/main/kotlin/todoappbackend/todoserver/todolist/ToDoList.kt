package todoappbackend.todoserver.todolist

import javax.persistence.*

@Entity
data class ToDoList(val name: String) {

    @OneToMany(cascade = [CascadeType.ALL])
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
