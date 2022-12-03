package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.level.LevelAlreadyExistsException;
import acc.inzynierka.exception.level.LevelDifficultyAlreadyExistsException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.webapp.LevelDto;
import acc.inzynierka.payload.request.webapp.LevelRequest;
import acc.inzynierka.payload.response.webapp.LevelResponse;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LevelService {

    private final LevelRepository levelRepository;

    private final CourseService courseService;

    private final StatusService statusService;

    public LevelService(LevelRepository levelRepository, CourseService courseService, StatusService statusService) {
        this.levelRepository = levelRepository;
        this.courseService = courseService;
        this.statusService = statusService;
    }

    public List<LevelDto> getAllLevels(Long courseID) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        List<LevelDto> levelDtoList = levelList.stream()
                .map(level -> new LevelDto(level.getId(),
                        level.getName(),
                        level.getDifficulty(),
                        level.getStatus().getName(),
                        level.getExercises().size(),
                        level.getFlashcards().size(),
                        level.getTestQuestions().size()))
                .collect(Collectors.toList());

        return levelDtoList;
    }

    public LevelDto getLevelById(Long id) {
        Level level = findById(id);

        LevelDto levelDto = (LevelDto) ObjectMapperUtil.mapToDTOSingle(level, LevelDto.class);
        levelDto.setExerciseNumber(level.getExercises().size());
        levelDto.setFlashcardNumber(level.getFlashcards().size());
        levelDto.setTestQuestionNumber(level.getTestQuestions().size());

        return levelDto;
    }

    public void deleteLevelById(Long id) {
        Level level = findById(id);

        levelRepository.delete(level);
    }

    public LevelResponse addLevel(Long courseID, LevelRequest levelRequest) {
        checkIfLevelNameIsUsed(courseID, levelRequest);
        checkIfLevelDifficultyIsUsed(courseID, levelRequest);

        Level level = new Level();

        level.setName(levelRequest.getName().toLowerCase());
        level.setDifficulty(levelRequest.getDifficulty());
        level.setStatus(statusService.findByName(levelRequest.getStatusName()));
        level.setCourse(courseService.findById(courseID));


        Level savedLevel = levelRepository.save(level);

        LevelResponse levelResponse = new LevelResponse();
        levelResponse.setLevel((LevelDto) ObjectMapperUtil.mapToDTOSingle(savedLevel, LevelDto.class));
        levelResponse.setMessage("Pomyślnie utworzono poziom");

        return levelResponse;
    }

    public LevelResponse editLevel(Long courseID, Long levelID, LevelRequest levelRequest) {
        Level level = findById(levelID);
        if (!level.getName().equalsIgnoreCase(levelRequest.getName())) {
            checkIfLevelNameIsUsed(courseID, levelRequest);
        }
        if (level.getDifficulty() != levelRequest.getDifficulty()) {
            checkIfLevelDifficultyIsUsed(courseID, levelRequest);
        }

        level.setName(levelRequest.getName().toLowerCase());
        level.setDifficulty(levelRequest.getDifficulty());
        level.setStatus(statusService.findByName(levelRequest.getStatusName()));

        levelRepository.save(level);

        LevelResponse levelResponse = new LevelResponse();
        levelResponse.setLevel((LevelDto) ObjectMapperUtil.mapToDTOSingle(level, LevelDto.class));
        levelResponse.setMessage("Pomyślnie zedytowano poziom");

        return levelResponse;
    }

    public void checkIfLevelNameIsUsed(Long courseID, LevelRequest levelRequest) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        Optional checkIfLevelExists = levelList.stream()
                .filter(level -> level.getName().equalsIgnoreCase(levelRequest.getName()))
                .findFirst();

        if (checkIfLevelExists.isPresent()) {
            throw new LevelAlreadyExistsException();
        }


    }

    public void checkIfLevelDifficultyIsUsed(Long courseID, LevelRequest levelRequest) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        Optional checkIfLevelDifficultyExists = levelList.stream()
                .filter(level -> level.getDifficulty() == levelRequest.getDifficulty())
                .findFirst();

        if (checkIfLevelDifficultyExists.isPresent()) {
            throw new LevelDifficultyAlreadyExistsException();
        }
    }

    public Level findById(long id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(LevelNotFoundException::new);

        return level;
    }

}
