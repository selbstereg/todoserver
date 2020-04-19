package todoappbackend.todoserver.utils

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import todoappbackend.todoserver.errorhandling.EntityNotFoundException

const val EXCEPTION_HANDLER_TEST_ENDPOINT = "/exception-handler-test"

@Controller
@RequestMapping(EXCEPTION_HANDLER_TEST_ENDPOINT)
class GlobalExceptionHandlerTestHelperController {

    @GetMapping()
    fun throwsException() {
        throw EntityNotFoundException(42L)
    }

}
