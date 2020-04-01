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

    // TODO Paul Bauknecht 1.4.2020: Add exception handler for case where to do list with given id is not in db
    @DeleteMapping("/{toDoListId}")
    fun deleteToDoList(@PathVariable toDoListId: Long): ToDoList {
        return toDoListCrudService.deleteToDoList(toDoListId)
    }
}
