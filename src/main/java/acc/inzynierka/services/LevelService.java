package acc.inzynierka.services;

import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.level.LevelAlreadyExistsException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.exception.status.StatusNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.LevelDto;
import acc.inzynierka.payload.request.LevelRequest;
import acc.inzynierka.payload.response.LevelResponse;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.repository.StatusRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StatusRepository statusRepository;

    public List<LevelDto> getAllLevels(Long courseID){
        Course course = courseRepository.findById(courseID)
                .orElseThrow(CourseNotFoundException::new);

        List<Level> levelList = course.getLevels();

        return ObjectMapperUtil.mapToDTO(levelList, LevelDto.class);
    }

    public LevelDto getLevelById(Long id){
        Level level = levelRepository.findById(id).orElseThrow(LevelNotFoundException::new);

        LevelDto levelDto = (LevelDto) ObjectMapperUtil.mapToDTOSingle(level, LevelDto.class);
        levelDto.setExerciseNumber(level.getExercises().size());
        levelDto.setFlashcardNumber(level.getFlashcards().size());
        levelDto.setTestQuestionNumber(level.getTestQuestions().size());

        return levelDto;
    }

    public void deleteLevelById(Long id){
        Level level = levelRepository.findById(id).orElseThrow(LevelNotFoundException::new);

        levelRepository.delete(level);
    }

    public LevelResponse addLevel(Long courseID, LevelRequest levelRequest){
        checkIfLevelNameIsUsed(courseID, levelRequest);

        Level level = new Level();

        level.setName(levelRequest.getName());
        level.setDifficulty(levelRequest.getDifficulty());
        level.setStatus(statusRepository.findByName(levelRequest.getStatusName())
                .orElseThrow(StatusNotFoundException::new));
        level.setCourse(courseRepository.findById(courseID)
                .orElseThrow(CourseNotFoundException::new));


        Level savedLevel = levelRepository.save(level);

        LevelResponse levelResponse = new LevelResponse();
        levelResponse.setLevel((LevelDto) ObjectMapperUtil.mapToDTOSingle(savedLevel, LevelDto.class));
        levelResponse.setMessage("Pomyślnie utworzono poziom");

        return levelResponse;
    }

    public void editLevel(Long courseID, Long levelID, LevelRequest levelRequest){
        Level level = levelRepository.findById(levelID).orElseThrow(LevelNotFoundException::new);
        if(!level.getName().equals(levelRequest.getName())){
            checkIfLevelNameIsUsed(courseID, levelRequest);
        }
        level.setName(levelRequest.getName());
        level.setDifficulty(levelRequest.getDifficulty());
        level.setStatus(statusRepository.findByName(levelRequest.getStatusName()).
                orElseThrow(StatusNotFoundException::new));

        levelRepository.save(level);
    }

    public void checkIfLevelNameIsUsed(Long courseID, LevelRequest levelRequest){
        Course course = courseRepository.findById(courseID)
                .orElseThrow(CourseNotFoundException::new);

        List<Level> levelList = course.getLevels();

        Optional checkIfLevelExists = levelList.stream()
                .filter(level -> level.getName().equals(levelRequest.getName()))
                .findFirst();

        if(checkIfLevelExists.isPresent()){
            throw new LevelAlreadyExistsException();
        }
    }

}
