package todoappbackend.todoserver.todolist.todo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ToDo(val name: String = "",
                @Id
                @GeneratedValue
                var id: Long? = null
)
