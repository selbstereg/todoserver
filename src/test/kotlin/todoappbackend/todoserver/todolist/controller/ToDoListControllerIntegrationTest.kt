package todoappbackend.todoserver.todolist.controller

import com.ninjasquad.springmockk.MockkBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import todoappbackend.todoserver.todolist.ToDoListCrudService

@WebMvcTest(controllers = [ToDoListController::class])
class ToDoListControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var toDoCrudServiceMock: ToDoListCrudService

    //TODO: Migrate tests from ToDoListControllerTest here!
}
