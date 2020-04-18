package todoappbackend.todoserver.todolist.controller

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.TODO_LIST_PATH
import todoappbackend.todoserver.todolist.TO_DOS_ENDPOINT
import todoappbackend.todoserver.todolist.ToDoListCrudService
import todoappbackend.todoserver.todolist.todo.ToDo

@RestController
@RequestMapping("$TODO_LIST_PATH/{toDoListId}/$TO_DOS_ENDPOINT")
class ToDoListItemController(
        private val toDoListCrudService: ToDoListCrudService
) {
    @PostMapping
    fun addToDo(@PathVariable toDoListId: Long, @RequestBody toDo: ToDo): ToDo {
        return toDoListCrudService.addToDo(toDoListId, toDo)
    }

    @DeleteMapping("/{toDoId}")
    fun deleteToDo(@PathVariable toDoListId: Long, @PathVariable toDoId: Long): ToDo {
        return toDoListCrudService.deleteToDo(toDoListId, toDoId)
    }
}