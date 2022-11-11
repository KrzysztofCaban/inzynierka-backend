package acc.inzynierka.services;

import acc.inzynierka.exception.level.LevelAlreadyExistsException;
import acc.inzynierka.exception.level.LevelDifficultyAlreadyExistsException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.LevelDto;
import acc.inzynierka.payload.request.LevelRequest;
import acc.inzynierka.payload.response.LevelResponse;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StatusService statusService;

    public List<LevelDto> getAllLevels(Long courseID) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        return ObjectMapperUtil.mapToDTO(levelList, LevelDto.class);
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
        checkIfLevelIsUsed(courseID, levelRequest);

        Level level = new Level();

        level.setName(levelRequest.getName());
        level.setDifficulty(levelRequest.getDifficulty());
        level.setStatus(statusService.findByName(levelRequest.getStatusName()));
        level.setCourse(courseService.findById(courseID));


        Level savedLevel = levelRepository.save(level);

        LevelResponse levelResponse = new LevelResponse();
        levelResponse.setLevel((LevelDto) ObjectMapperUtil.mapToDTOSingle(savedLevel, LevelDto.class));
        levelResponse.setMessage("Pomyślnie utworzono poziom");

        return levelResponse;
    }

    public void editLevel(Long courseID, Long levelID, LevelRequest levelRequest) {
        Level level = findById(levelID);
        if (!level.getName().equals(levelRequest.getName())) {
            checkIfLevelIsUsed(courseID, levelRequest);
        }
        level.setName(levelRequest.getName());
        level.setDifficulty(levelRequest.getDifficulty());
        level.setStatus(statusService.findByName(levelRequest.getStatusName()));

        levelRepository.save(level);
    }

    public void checkIfLevelIsUsed(Long courseID, LevelRequest levelRequest) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        Optional checkIfLevelExists = levelList.stream()
                .filter(level -> level.getName().equals(levelRequest.getName()))
                .findFirst();

        if (checkIfLevelExists.isPresent()) {
            throw new LevelAlreadyExistsException();
        }

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
