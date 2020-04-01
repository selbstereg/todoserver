package todoappbackend.todoserver.todolist

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ToDoList(val name: String) {
    @Id
    @GeneratedValue
    val id: Long? = null
}
