package todoappbackend.todoserver.todolist.controller

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.service.ToDoListItemService
import todoappbackend.todoserver.todolist.todo.ToDo

@RestController
@RequestMapping("$TO_DO_LIST_PATH/{toDoListId}/$TO_DOS_ENDPOINT")
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