package todoappbackend.todoserver.service

import org.springframework.stereotype.Component
import todoappbackend.todoserver.model.ToDoList

@Component
class ToDoListCrudService {
    fun getToDoLists(): Collection<ToDoList> {
        return listOf(ToDoList("Einkaufsliste"))
    }
}
