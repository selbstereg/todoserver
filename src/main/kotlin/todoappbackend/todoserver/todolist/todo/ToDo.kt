package todoappbackend.todoserver.todolist.todo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// INTERESTING: For data classes, only the properties in the primary constructor are considered
// for the generation of hashCode and Equals. Check out ToDoListIntegrationTest.kt to understand,
// why we put the id into the primary constructor.
// We provide the default value null, so you can still create a to do by only providing a name.

@Entity
data class ToDo(
        val name: String = "",
        @Id
        @GeneratedValue
        var id: Long? = null
)
