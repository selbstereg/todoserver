package todoappbackend.todoserver.todolist

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/to-do-lists")
class ToDoListController(
        @Autowired
        private val toDoListCrudService: ToDoListCrudService
) {
    @GetMapping()
    fun getToDoLists(): Collection<ToDoList> {
        return toDoListCrudService.getToDoLists()
    }

    @PostMapping()
    fun createToDoList(@RequestBody name: String): ToDoList {
        return toDoListCrudService.createToDoList(name)
    }
}
