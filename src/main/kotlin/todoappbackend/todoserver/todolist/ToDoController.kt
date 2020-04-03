package todoappbackend.todoserver.todolist

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.todo.ToDo

@RestController
@RequestMapping("$TODOLIST_PATH/{toDoListId}/$TODOS_ENDPOINT")
class ToDoController(
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