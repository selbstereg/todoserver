package todoappbackend.todoserver.todolist.controller

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.TO_DO_LIST_PATH
import todoappbackend.todoserver.todolist.ToDoList
import todoappbackend.todoserver.todolist.ToDoListService

@RestController
@RequestMapping(TO_DO_LIST_PATH)
class ToDoListController(
        private val toDoListService: ToDoListService
) {
    @GetMapping
    fun getToDoLists(): Collection<ToDoList> {
        return toDoListService.getToDoLists()
    }

    @PostMapping
    fun createToDoList(@RequestBody name: String): ToDoList {
        return toDoListService.createToDoList(name)
    }

    @DeleteMapping("/{toDoListId}")
    fun deleteToDoList(@PathVariable toDoListId: Long): ToDoList {
        return toDoListService.deleteToDoList(toDoListId)
    }
}
