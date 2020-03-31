package todoappbackend.todoserver.service

import org.springframework.stereotype.Component

@Component
class ToDoListCrudServiceImpl : ToDoListCrudService {
    override fun hello() {
        println("hello :)")
    }
}
