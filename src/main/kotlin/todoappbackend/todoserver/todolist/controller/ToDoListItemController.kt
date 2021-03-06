package todoappbackend.todoserver.todolist.controller

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.ENDPOINT_TO_DOS
import todoappbackend.todoserver.todolist.URI_PATH_TO_DO_LISTS
import todoappbackend.todoserver.todolist.service.ToDoListItemService
import todoappbackend.todoserver.todolist.todo.ToDo

@RestController
@RequestMapping("$URI_PATH_TO_DO_LISTS/{toDoListId}/$ENDPOINT_TO_DOS")
class ToDoListItemController(
        private val toDoListItemService: ToDoListItemService
) {
    @GetMapping
    fun getToDos(@PathVariable toDoListId: Long): List<ToDo> {
        return toDoListItemService.getToDos(toDoListId)
    }

    @PostMapping
    fun addToDo(@PathVariable toDoListId: Long, @RequestBody toDo: ToDo): ToDo {
        return toDoListItemService.addToDo(toDoListId, toDo)
    }

    @DeleteMapping("/{toDoId}")
    fun deleteToDo(@PathVariable toDoListId: Long, @PathVariable toDoId: Long): ToDo {
        return toDoListItemService.deleteToDo(toDoListId, toDoId)
    }
}