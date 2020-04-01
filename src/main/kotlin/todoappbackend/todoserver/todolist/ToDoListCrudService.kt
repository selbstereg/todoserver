package todoappbackend.todoserver.todolist

import org.springframework.stereotype.Component

@Component
class ToDoListCrudService(val toDoListRepo: ToDoListRepo) {

    fun getToDoLists(): Collection<ToDoList> {
        return toDoListRepo.findAll()
    }
}
