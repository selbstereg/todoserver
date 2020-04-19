package todoappbackend.todoserver.todolist.todo

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import todoappbackend.todoserver.errorhandling.EntityNotFoundException

@Component
class ToDoService(val repo: ToDoRepo) {
    fun updatePriority(toDoId: Long, priority: Int) {
        val toDo = repo.findByIdOrNull(toDoId)
        toDo ?: throw EntityNotFoundException(toDoId)

        toDo.priority = priority
        repo.save(toDo)
    }
}
