package todoappbackend.todoserver.utils

import io.mockk.clearAllMocks
import org.junit.jupiter.api.AfterEach

open class TestWithMockkMocks {

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

}