package todoappbackend.todoserver.errorhandling

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ResponseEntity<String> {
        // TODO Paul Bauknecht 18 Apr 2020: Make sure, that this is compatible to the frontend client
        // which should not end up with redundant quotes
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("\"${exception.message}\"")
    }
}