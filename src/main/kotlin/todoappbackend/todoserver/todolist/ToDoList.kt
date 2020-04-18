package todoappbackend.todoserver.todolist

import todoappbackend.todoserver.todolist.todo.ToDo
import javax.persistence.*

// INTERESTING: Uncle Bob tells us not to mix command and query. Therefore
// find() and remove() are separate methods, although they are currently
// only used together to remove a to do by id.
// In Kotlin, the type of a property must be the same as the return type
// of its getter. Therefore, the property toDos (immutable list) is backed
// by the (mutable) field _toDos. You can see, what's in the list, but
// need to use the public API of ToDoList for mutation.

@Entity
data class ToDoList(val name: String) {

    @OneToMany(cascade = [CascadeType.PERSIST], orphanRemoval = true)
    private val _toDos: MutableList<ToDo> = mutableListOf()
    val toDos: List<ToDo>
        get() {
            return _toDos
        }

    @Id
    @GeneratedValue
    val id: Long? = null

    fun add(toDo: ToDo) {
        _toDos.add(toDo)
    }

    fun find(toDoId: Long): ToDo? {
        return _toDos.find { it.id == toDoId }
    }

    fun remove(toDo: ToDo): Boolean {
        return _toDos.remove(toDo)
    }

}
