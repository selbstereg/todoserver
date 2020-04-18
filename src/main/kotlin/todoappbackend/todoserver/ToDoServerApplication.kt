package todoappbackend.todoserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// INTERESTING: The Kotlin-Koans course is great to learn KotlinUm Kotlin.
// You can also take it several times. This way you get a good Overview of the
// special features and the advantages that Kotlin has over Java.
// The course can be taken directly in IntelliJ. The necessary content is
// downloaded automatically.

@SpringBootApplication
class ToDoServerApplication

// TODO Paul Bauknecht 18 Apr 2020: Dig into startup exceptions regarding altering of schema

fun main(args: Array<String>) {
	runApplication<ToDoServerApplication>(*args)
}
