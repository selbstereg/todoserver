package todoappbackend.todoserver.todolist

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

// TODO: On App startup, Hibernate complains, that the getter for property "name" is final, as it
// need to override it for lazy initialization. What should this class look like, so we benefit
// from null safety, JPA is happy and it is hard for a developer
// to instantiate an uninitialized instance?
@Entity
open class ToDoList(val name: String) {
    // For JPA provider
    protected constructor() : this("")

    @Id
    @GeneratedValue
    val id: Long? = null
}
