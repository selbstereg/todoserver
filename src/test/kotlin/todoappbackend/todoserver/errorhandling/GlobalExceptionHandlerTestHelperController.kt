package todoappbackend.todoserver.errorhandling

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GlobalExceptionHandlerTestHelperController {

    @GetMapping("/test-endpoint")
    fun throwsException() {
        throw EntityNotFoundException(42L)
    }

}
