package todoappbackend.todoserver.todolist

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ToDoListCrudService(val toDoListRepo: ToDoListRepo) {

    fun getToDoLists(): Collection<ToDoList> {
        return toDoListRepo.findAll()
    }

    fun createToDoList(name: String): ToDoList {
        return toDoListRepo.save(ToDoList(name))
    }

    fun deleteToDoList(id: Long): ToDoList {
        val toDoList = toDoListRepo.findByIdOrNull(id)
        toDoList ?: throw EntityNotFoundException(id)
        toDoListRepo.delete(toDoList)
        return toDoList
    }

    fun addToDo(toToDoListId: Long, toDo: ToDo): ToDoList {
        return ToDoList("")
    }
}
