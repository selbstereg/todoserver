package todoappbackend.todoserver.todolist

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class ToDoList(val name: String) {
    // For JPA
    protected constructor() : this("")

    @Id
    @GeneratedValue
    val id: Long? = null
}
