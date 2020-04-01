package todoappbackend.todoserver.todolist

import org.springframework.stereotype.Component

@Component
class ToDoListCrudService(val toDoListRepo: ToDoListRepo) {

    fun getToDoLists(): Collection<ToDoList> {
        return toDoListRepo.findAll()
    }

    fun createToDoList(name: String): ToDoList {
        return toDoListRepo.save(ToDoList(name))
    }

    fun deleteToDoList(id: Long): ToDoList {
        return ToDoList(id.toString())
    }
}
