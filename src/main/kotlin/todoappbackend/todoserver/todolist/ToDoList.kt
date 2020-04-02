package todoappbackend.todoserver.todolist

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class ToDoList(val name: String) {
    @OneToMany
    var todos: MutableSet<ToDo> = mutableSetOf()

    @Id
    @GeneratedValue
    val id: Long? = null

    fun add(toDo: ToDo) {
        todos.add(toDo)
    }

}
