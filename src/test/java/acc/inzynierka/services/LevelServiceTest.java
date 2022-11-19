package acc.inzynierka.services;

import acc.inzynierka.exception.level.LevelAlreadyExistsException;
import acc.inzynierka.exception.level.LevelDifficultyAlreadyExistsException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.Level;
import acc.inzynierka.models.Status;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.modelsDTO.LevelDto;
import acc.inzynierka.payload.request.LevelRequest;
import acc.inzynierka.payload.response.LevelResponse;
import acc.inzynierka.repository.LevelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

class LevelServiceTest {

    CourseService courseService = Mockito.mock(CourseService.class);
    StatusService statusService = Mockito.mock(StatusService.class);
    LevelRepository levelRepository = Mockito.mock(LevelRepository.class);
    LevelService levelService;

    @BeforeEach
    void setUp() {
        levelService = new LevelService(levelRepository, courseService, statusService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllLevels() {
        Level level1 = new Level();
        Level level2 = new Level();

        level1.setId(1L);
        level1.setName("test1");
        level1.setStatus(new Status(1L, EStatus.STATUS_SUSPENDED));
        level1.setExercises(new ArrayList<>());
        level1.setFlashcards(new ArrayList<>());
        level1.setTestQuestions(new ArrayList<>());

        level2.setId(2L);
        level2.setName("test2");
        level2.setStatus(new Status(1L, EStatus.STATUS_SUSPENDED));
        level2.setExercises(new ArrayList<>());
        level2.setFlashcards(new ArrayList<>());
        level2.setTestQuestions(new ArrayList<>());

        List<Level> levelList = new ArrayList<>();
        levelList.add(level1);
        levelList.add(level2);

        Course course = new Course();
        course.setId(1L);
        course.setLevels(levelList);


        Mockito.when(levelRepository.findAll()).thenReturn(levelList);
        Mockito.when(courseService.findById(Mockito.any(Long.TYPE))).thenReturn(course);
        List<LevelDto> levelDtoList = levelService.getAllLevels(1L);

        assertEquals(2, levelDtoList.size());
        assertEquals("test1", levelDtoList.get(0).getName());
    }

    @Test
    void getLevelById() {
        Level level = new Level();

        level.setId(1L);
        level.setName("test");
        level.setExercises(new ArrayList<>());
        level.setFlashcards(new ArrayList<>());
        level.setTestQuestions(new ArrayList<>());

        Mockito.when(levelRepository.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.of(level));
        LevelDto levelDto = levelService.getLevelById(1L);
        assertEquals("test", levelDto.getName(), "Nazwa kursu to nie jest 'test'");
        assertEquals(0, levelDto.getExerciseNumber(), "Liczba ćwiczeń w poziomie jest różna od 0");
    }

    @Test
    void deleteLevelById() {
        Level level = new Level();
        level.setId(1L);

        Mockito.when(levelRepository.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.of(level));
        levelService.deleteLevelById(1L);
        Mockito.verify(levelRepository).delete(level);
    }

    @Test
    void addLevel() {
        LevelRequest levelRequest = new LevelRequest();
        levelRequest.setName("testMock");
        levelRequest.setDifficulty(10);
        levelRequest.setStatusName(EStatus.STATUS_SUSPENDED);

        Course course = new Course();
        course.setId(1L);
        course.setLevels(new ArrayList<>());

        Mockito.when(levelRepository.save(Mockito.any(Level.class))).then(returnsFirstArg());
        Mockito.when(courseService.findById(Mockito.any(Long.TYPE))).thenReturn(course);
        LevelResponse created = levelService.addLevel(1L, levelRequest);

        assertFalse(created.getLevel().getName().isEmpty());
    }

    @Test
    void editLevel() {
        LevelRequest levelRequest = new LevelRequest("testLevel", 1, EStatus.STATUS_SUSPENDED);

        Level level = new Level();

        level.setId(1L);
        level.setName("test");
        level.setExercises(new ArrayList<>());
        level.setFlashcards(new ArrayList<>());
        level.setTestQuestions(new ArrayList<>());

        Course course = new Course();
        course.setId(1L);
        course.setLevels(Collections.singletonList(level));

        Mockito.when(levelRepository.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.of(level));
        Mockito.when(courseService.findById(Mockito.any(Long.TYPE))).thenReturn(course);
        levelService.editLevel(1L, 1L, levelRequest);
        assertTrue(true, "Nie udało się edytwać poziomu");
    }

    @Test
    void checkIfLevelNameIsUsedExceptionThrown() {
        Level level1 = new Level();

        level1.setId(1L);
        level1.setName("test1");
        level1.setStatus(new Status(1L, EStatus.STATUS_SUSPENDED));
        level1.setExercises(new ArrayList<>());
        level1.setFlashcards(new ArrayList<>());
        level1.setTestQuestions(new ArrayList<>());

        List<Level> levelList = new ArrayList<>();
        levelList.add(level1);

        Course course = new Course();
        course.setId(1L);
        course.setLevels(levelList);

        LevelRequest levelRequest = new LevelRequest("test1", 1, EStatus.STATUS_SUSPENDED);

        Mockito.when(courseService.findById(Mockito.any(Long.TYPE))).thenReturn(course);


        Throwable exception = assertThrows(LevelAlreadyExistsException.class,
                () -> levelService.checkIfLevelNameIsUsed(1L, levelRequest));
        assertEquals("Nazwa poziomu jest już w użyciu", exception.getMessage());
    }

    @Test
    void checkIfLevelDifficultyIsUsedExceptionThrown() {
        Level level1 = new Level();

        level1.setId(1L);
        level1.setName("test1");
        level1.setStatus(new Status(1L, EStatus.STATUS_SUSPENDED));
        level1.setDifficulty(1);
        level1.setExercises(new ArrayList<>());
        level1.setFlashcards(new ArrayList<>());
        level1.setTestQuestions(new ArrayList<>());

        List<Level> levelList = new ArrayList<>();
        levelList.add(level1);

        Course course = new Course();
        course.setId(1L);
        course.setLevels(levelList);

        LevelRequest levelRequest = new LevelRequest("test2", 1, EStatus.STATUS_SUSPENDED);

        Mockito.when(courseService.findById(Mockito.any(Long.TYPE))).thenReturn(course);


        Throwable exception = assertThrows(LevelDifficultyAlreadyExistsException.class,
                () -> levelService.checkIfLevelDifficultyIsUsed(1L, levelRequest));
        assertEquals("Poziom o podanym poziomie trudności jest już w użyciu w obecnym kursie",
                exception.getMessage());
    }

    @Test
    void findById() {
        Level level2 = new Level();

        level2.setId(2L);
        level2.setName("test2");
        level2.setStatus(new Status(1L, EStatus.STATUS_SUSPENDED));
        level2.setExercises(new ArrayList<>());
        level2.setFlashcards(new ArrayList<>());
        level2.setTestQuestions(new ArrayList<>());

        Mockito.when(levelRepository.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.of(level2));

        Level level = levelService.findById(2L);

        assertEquals(2L, (long) level.getId());

    }

    @Test
    void findByIdExceptionThrown() {

        Mockito.when(levelRepository.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.empty());
        Exception exception = assertThrows(LevelNotFoundException.class, () -> {
            levelService.findById(2L);
        });
        assertEquals("Nie znaleziono poziomu", exception.getMessage());
    }
}