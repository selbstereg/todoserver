package todoappbackend.todoserver.todolist.todo

import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.todolist.URI_PATH_TO_DOS


@RestController
@RequestMapping(URI_PATH_TO_DOS)
class ToDoController(val toDoService: ToDoService) {

    @PutMapping("/{toDoId}/priority")
    fun updatePriority(@PathVariable toDoId: Long, @RequestBody priority: Int) {
        toDoService.updatePriority(toDoId, priority)
    }
}
