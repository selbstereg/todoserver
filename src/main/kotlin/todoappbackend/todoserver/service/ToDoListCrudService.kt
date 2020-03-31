package todoappbackend.todoserver.service

import todoappbackend.todoserver.model.ToDoList

interface ToDoListCrudService {
    fun getToDoLists(): Collection<ToDoList>
}

