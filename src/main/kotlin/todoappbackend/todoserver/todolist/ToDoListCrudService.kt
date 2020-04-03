package todoappbackend.todoserver.todolist

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import todoappbackend.todoserver.exceptions.EntityNotFoundException
import todoappbackend.todoserver.todolist.todo.ToDo
import todoappbackend.todoserver.todolist.todo.ToDoRepo

@Component
class ToDoListCrudService(
        val toDoListRepo: ToDoListRepo,
        val toDoRepo: ToDoRepo
) {

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
        val savedToDo = toDoRepo.save(toDo)

        val toDoList = getToDoList(toDoListId)
        toDoList.add(savedToDo)
        toDoListRepo.save(toDoList)

        return savedToDo
    }

    private fun getToDoList(id: Long): ToDoList {
        val toDoList = toDoListRepo.findByIdOrNull(id)
        toDoList ?: throw EntityNotFoundException(id)
        return toDoList
    }
}
