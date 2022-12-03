package acc.inzynierka.controllers;

import acc.inzynierka.InzynierkaApplication;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.payload.request.webapp.CourseRequest;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InzynierkaApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithUserDetails(value = "testSuperAdmin")
    @Order(6)
    void getAllCourses() throws Exception {
        mockMvc.perform(get("/api/course/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.[0].name", Matchers.is("test")));
    }

    @Test
    @WithUserDetails(value = "test1234")
    @Order(5)
    void getAllAdminCourses() throws Exception {
        mockMvc.perform(get("/api/course/admin").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("test")));
    }

    @Test
    @WithUserDetails(value = "test1234")
    @Order(4)
    void getCourseById() throws Exception {
        mockMvc.perform(get("/api/course/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("test")));

    }

    @Test
    @WithUserDetails(value = "test1234")
    @Order(1)
    void createCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CourseRequest courseRequest = new CourseRequest("testMock", "testMock", "KAT1", EStatus.STATUS_SUSPENDED);

        mockMvc.perform(post("/api/course/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Matchers.is("Pomyślnie utworzono kurs")));
    }

    @Test
    @WithUserDetails(value = "test1234")
    @Order(2)
    void editCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Course course = courseRepository.findByName("testMock").orElse(null);

        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setName(course.getName());
        courseRequest.setDescription("new mock description");
        courseRequest.setCategoryName(course.getCategory().getName());
        courseRequest.setStatusName(course.getStatus().getName());

        mockMvc.perform(patch("/api/course/edit/" + course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Matchers.is("Pomyślnie zedytowano kurs")));

    }

    @Test
    @WithUserDetails(value = "test1234")
    @Order(3)
    void deleteCourseById() throws Exception {
        Course course = courseRepository.findByName("testMock").orElse(null);

        mockMvc.perform(delete("/api/course/delete/" + course.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Matchers.is("Pomyślnie usunięto kurs")));
    }
}