package acc.inzynierka.controllers;

import acc.inzynierka.InzynierkaApplication;
import acc.inzynierka.models.Level;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.payload.request.webapp.LevelRequest;
import acc.inzynierka.repository.LevelRepository;
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
class LevelControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithUserDetails(value = "test1234")
    @Order(5)
    void getAllCoursesForAdmin() throws Exception {
        mockMvc.perform(get("/api/course/1/level/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.[0].name", Matchers.is("test lvl3")))
                .andExpect(jsonPath("$.[0].difficulty", Matchers.is(3)));
    }

    @Test
    @WithUserDetails(value = "testFlutter6")
    @Order(6)
    void getAllCoursesWithNoAccessForUser() throws Exception {
        mockMvc.perform(get("/api/course/25/level/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", Matchers.is("Unauthorized")));
    }

    @Test
    @WithUserDetails(value = "testSuperAdmin")
    @Order(1)
    void addNewCourse() throws Exception {

        LevelRequest levelRequest = new LevelRequest("LevelTestMock", 9, EStatus.STATUS_SUSPENDED);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/course/25/level/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(levelRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = "testSuperAdmin")
    @Order(2)
    void editLevel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Level level = levelRepository.findByName("LevelTestMock").orElse(null);

        LevelRequest levelRequest = new LevelRequest();
        levelRequest.setName(level.getName());
        levelRequest.setDifficulty(8);
        levelRequest.setStatusName(level.getStatus().getName());

        mockMvc.perform(patch("/api/course/25/level/edit/" + level.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(levelRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Pomyślnie zedytowano poziom")))
                .andExpect(jsonPath("$.level.difficulty", is(8)));

    }

    @Test
    @WithUserDetails(value = "testSuperAdmin")
    @Order(2)
    void getLevelById() throws Exception {

        Level level = levelRepository.findByName("LevelTestMock").orElse(null);

        mockMvc.perform(get("/api/course/25/level/" + level.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("LevelTestMock")));

    }

    @Test
    @WithUserDetails(value = "testSuperAdmin")
    @Order(4)
    void deleteCourse() throws Exception {

        Level level = levelRepository.findByName("LevelTestMock").orElse(null);

        mockMvc.perform(delete("/api/course/25/level/delete/" + level.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", Matchers.is("Pomyślnie usunięto poziom")));
    }


}
