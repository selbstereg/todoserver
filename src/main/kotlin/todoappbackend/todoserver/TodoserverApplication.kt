package todoappbackend.todoserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoserverApplication

fun main(args: Array<String>) {
	runApplication<TodoserverApplication>(*args)
}
