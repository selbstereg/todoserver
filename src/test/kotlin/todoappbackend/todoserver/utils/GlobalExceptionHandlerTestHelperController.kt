package todoappbackend.todoserver.utils

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import todoappbackend.todoserver.errorhandling.EntityNotFoundException

@Controller
class GlobalExceptionHandlerTestHelperController {

    @GetMapping("/test-endpoint")
    fun throwsException() {
        throw EntityNotFoundException(42L)
    }

}
