package todoappbackend.todoserver.todolist.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import todoappbackend.todoserver.errorhandling.EntityNotFoundException
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.ToDoListRepo

@Component
class ToDoListService (
        val toDoListRepo: ToDoListRepo
) {
    @Synchronized
    fun createToDoList(name: String): ToDoList {
        return toDoListRepo.save(ToDoList(name))
    }

    fun getToDoLists(): Collection<ToDoList> {
        return toDoListRepo.findAll()
    }

    fun getToDoList(id: Long): ToDoList {
        val toDoList = toDoListRepo.findByIdOrNull(id)
        toDoList ?: throw EntityNotFoundException(id)
        return toDoList
    }

    fun save(toDoList: ToDoList): ToDoList {
        return toDoListRepo.save(toDoList)
    }

    fun deleteToDoList(id: Long): ToDoList {
        val toDoList = getToDoList(id)
        toDoListRepo.delete(toDoList)
        return toDoList
    }
}