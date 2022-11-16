package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.level.LevelAlreadyExistsException;
import acc.inzynierka.exception.level.LevelDifficultyAlreadyExistsException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.mobileapp.LevelMobileDto;
import acc.inzynierka.modelsDTO.webapp.LevelDto;
import acc.inzynierka.payload.request.webapp.LevelRequest;
import acc.inzynierka.payload.response.webapp.LevelResponse;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.services.webapp.CourseService;
import acc.inzynierka.services.webapp.StatusService;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LevelMobileService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private CourseService courseService;


    public List<LevelMobileDto> getAllLevels(Long courseID) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        List<LevelMobileDto> levelMobileDtoList = levelList.stream()
                .map(level -> new LevelMobileDto(
                        level.getName(),
                        level.getDifficulty(),
                        level.getExercises().size(),
                        level.getFlashcards().size(),
                        level.getTestQuestions().size()))
                .collect(Collectors.toList());

        return levelMobileDtoList;
    }

    public LevelMobileDto getLevelById(Long id) {
        Level level = findById(id);

        LevelMobileDto levelMobileDto = (LevelMobileDto) ObjectMapperUtil.mapToDTOSingle(level, LevelMobileDto.class);
        levelMobileDto.setExerciseNumber(level.getExercises().size());
        levelMobileDto.setFlashcardNumber(level.getFlashcards().size());
        levelMobileDto.setTestQuestionNumber(level.getTestQuestions().size());

        return new LevelMobileDto();
    }

    public Level findById(long id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(LevelNotFoundException::new);

        return level;
    }

}
