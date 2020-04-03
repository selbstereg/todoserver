package todoappbackend.todoserver.todolist

import javax.persistence.*

@Entity
data class ToDoList(val name: String) {

    @OneToMany(cascade = [CascadeType.ALL])
    var todos: MutableSet<ToDo> = mutableSetOf()

    @Id
    @GeneratedValue
    val id: Long? = null

    fun add(toDo: ToDo) {
        todos.add(toDo)
    }

}
