package todoappbackend.todoserver.todolist.todo

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// INTERESTING: For data classes, only the properties in the primary constructor are considered
// for the generation of hashCode and Equals. Check out ToDoListIntegrationTest.kt to understand,
// why we put the id into the primary constructor.
// We provide the default value null, so you can still create a to do by only providing a name.

// TODO Paul Bauknecht 19 Apr 2020: When jackson unmarshalls an incoming ToDo from json, and there is no
// priority property, it just sets it to 0. Actually this should result in a 400 bad request, ideally
// with an error message, that gives you a hint, what went wrong!

@Entity
data class ToDo(
        val name: String,
        @Column(length = 4095)
        val details: String = "",
        var priority: Int,
        @Id
        @GeneratedValue
        var id: Long? = null
) : Comparable<ToDo> {
        override fun compareTo(other: ToDo): Int =
                priority.compareTo(other.priority)
}
