package todoappbackend.todoserver.todolist

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import todoappbackend.todoserver.exceptions.EntityNotFoundException
import todoappbackend.todoserver.todolist.todo.ToDo

@RestController
@RequestMapping("/api/to-do-lists")
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

    @PostMapping("/{toDoListId}/to-dos")
    fun addToDo(@PathVariable toDoListId: Long, @RequestBody toDo: ToDo): ToDo {
        return toDoListCrudService.addToDo(toDoListId, toDo)
    }

    @DeleteMapping("/{toDoListId}/to-dos/{toDoId}")
    fun deleteToDo(@PathVariable toDoListId: Long, @PathVariable toDoId: Long): ToDo {
        return toDoListCrudService.deleteToDo(toDoListId, toDoId)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ResponseEntity<String> {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("\"${exception.message}\"")
    }
}
