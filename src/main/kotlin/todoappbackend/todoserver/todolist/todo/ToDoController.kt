package todoappbackend.todoserver.todolist.todo

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.errorhandling.EntityNotFoundException
import todoappbackend.todoserver.todolist.URI_PATH_TO_DOS


@RestController
@RequestMapping(URI_PATH_TO_DOS)
class ToDoController(val toDoService: ToDoService) {

    @PutMapping("/{toDoId}/priority")
    fun updatePriority(@PathVariable toDoId: Long, @RequestBody priority: Int) {
        toDoService.updatePriority(toDoId, priority)
    }

    @PutMapping()
    fun updateToDo(@RequestBody toDo: ToDo): ToDo {
        toDo.id ?: throw IllegalArgumentException("No id set when trying to update to do: $toDo")
        return toDoService.update(toDo)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body("\"${exception.message}\"")
    }
}
