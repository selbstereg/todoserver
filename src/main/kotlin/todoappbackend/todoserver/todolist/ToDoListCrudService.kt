package todoappbackend.todoserver.todolist

import org.springframework.stereotype.Component
import todoappbackend.todoserver.todolist.ToDoList

@Component
class ToDoListCrudService {
    fun getToDoLists(): Collection<ToDoList> {
        return listOf(ToDoList("Einkaufsliste"))
    }
}
