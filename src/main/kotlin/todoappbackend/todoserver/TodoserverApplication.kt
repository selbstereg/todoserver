package todoappbackend.todoserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// INTERESTING: The Kotlin-Koans course is great to learn KotlinUm Kotlin.
// You can also take it several times. This way you get a good Overview of the
// special features and the advantages that Kotlin has over Java.
// The course can be taken directly in IntelliJ. The necessary content is
// downloaded automatically.

@SpringBootApplication
class TodoserverApplication

fun main(args: Array<String>) {
	runApplication<TodoserverApplication>(*args)
}
