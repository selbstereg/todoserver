package todoappbackend.todoserver.todolist

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ToDo(val name: String = "") {
    @Id
    var id: Long? = null
}
