package todoappbackend.todoserver.todolist.todo

import org.springframework.data.jpa.repository.JpaRepository

interface ToDoRepo : JpaRepository<ToDo, Long>
