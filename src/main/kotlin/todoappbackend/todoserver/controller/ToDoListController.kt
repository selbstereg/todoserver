package todoappbackend.todoserver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import todoappbackend.todoserver.model.ToDoList
import todoappbackend.todoserver.service.ToDoListCrudService

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
}
