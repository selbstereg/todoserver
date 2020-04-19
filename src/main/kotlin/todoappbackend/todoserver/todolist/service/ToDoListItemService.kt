package todoappbackend.todoserver.todolist.service

import org.springframework.stereotype.Component
import todoappbackend.todoserver.errorhandling.EntityNotFoundException
import todoappbackend.todoserver.todolist.todo.ToDo
import todoappbackend.todoserver.todolist.todo.ToDoRepo

@Component
class ToDoListItemService(
        val toDoListService: ToDoListService,
        val toDoRepo: ToDoRepo
) {

    // TODO Paul Bauknecht 19 Apr 2020: Try to write a test, that creates a lot of to dos concurrently
    // This can cause trouble with the db, as it assigns the same id to new entities, which are
    // added "at the same time".
    @Synchronized
    fun addToDo(toDoListId: Long, toDo: ToDo): ToDo {
        val savedToDo = toDoRepo.save(toDo)

        val toDoList = toDoListService.getToDoList(toDoListId)
        toDoList.add(savedToDo)
        toDoListService.save(toDoList)

        return savedToDo
    }

    fun getToDos(toDoListId: Long): List<ToDo> {
        val toDoList = toDoListService.getToDoList(toDoListId)
        return toDoList.toDos
    }

    fun deleteToDo(toDoListId: Long, toDoId: Long): ToDo {
        val toDoList = toDoListService.getToDoList(toDoListId)

        val toDo = toDoList.find(toDoId)
        toDo ?: throw EntityNotFoundException(toDoId)
        toDoList.remove(toDo)
        toDoListService.save(toDoList)

        return toDo
    }
}
