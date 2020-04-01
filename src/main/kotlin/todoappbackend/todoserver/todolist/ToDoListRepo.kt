package todoappbackend.todoserver.todolist

import org.springframework.data.jpa.repository.JpaRepository

interface ToDoListRepo : JpaRepository<ToDoList, Long>
