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
        val toDoList = getToDoList(id)
        toDoListRepo.delete(toDoList)
        return toDoList
    }

    fun addToDo(toDoListId: Long, toDo: ToDo): ToDo {
        val toDoList = getToDoList(toDoListId)
        toDoList.add(toDo)
        toDoListRepo.save(toDoList)
        return toDo
    }

    private fun getToDoList(id: Long): ToDoList {
        val toDoList = toDoListRepo.findByIdOrNull(id)
        toDoList ?: throw EntityNotFoundException(id)
        return toDoList
    }
}
