package todoappbackend.todoserver.todolist.controller

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.TODOLIST_PATH
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.ToDoListCrudService

@RestController
@RequestMapping(TODOLIST_PATH)
class ToDoListController(
        private val toDoListCrudService: ToDoListCrudService
) {
    @GetMapping
    fun getToDoLists(): Collection<ToDoList> {
        return toDoListCrudService.getToDoLists()
    }

    @PostMapping
    fun createToDoList(@RequestBody name: String): ToDoList {
        return toDoListCrudService.createToDoList(name)
    }

    @DeleteMapping("/{toDoListId}")
    fun deleteToDoList(@PathVariable toDoListId: Long): ToDoList {
        return toDoListCrudService.deleteToDoList(toDoListId)
    }
}
