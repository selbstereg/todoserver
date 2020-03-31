package todoappbackend.todoserver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import todoappbackend.todoserver.service.ToDoListCrudService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/to-do-lists")
class ToDoListController(
        @Autowired
        private val toDoListCrudService: ToDoListCrudService
) {
    @GetMapping()
    fun getToDoLists() {
        toDoListCrudService.hello()
    }
}
