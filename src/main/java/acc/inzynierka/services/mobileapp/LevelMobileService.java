package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Course;
import acc.inzynierka.models.Level;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.modelsDTO.mobileapp.LevelMobileDto;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.repository.ResultRepository;
import acc.inzynierka.services.webapp.CourseService;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelMobileService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ResultRepository resultRepository;

    public List<LevelMobileDto> getAllLevels(Long courseID) {
        Course course = courseService.findById(courseID);

        List<Level> levelList = course.getLevels();

        List<LevelMobileDto> levelMobileDtoList = levelList.stream()
                .filter(level -> level.getStatus().getName().equals(EStatus.STATUS_ACTIVE))
                .map(level -> {

            int result = findResultforLevel(level.getId());

            return new LevelMobileDto(level.getId(), level.getName(), level.getDifficulty(), result);
        }).collect(Collectors.toList());

        return levelMobileDtoList;
    }

    public LevelMobileDto getLevelById(Long id) {
        Level level = findById(id);

        LevelMobileDto levelMobileDto = (LevelMobileDto) ObjectMapperUtil.mapToDTOSingle(level, LevelMobileDto.class);
        levelMobileDto.setBestResult(findResultforLevel(level.getId()));

        return levelMobileDto;
    }

    public Integer findResultforLevel(Long levelId) {
        Long userId = UserUtil.getUser();
        int result = 0;
        if (resultRepository.existsByUser_IdAndLevel_Id(userId, levelId))
            result = resultRepository.findByUser_IdAndLevel_Id(userId, levelId).getValue();
        return result;
    }

    public Level findById(long id) {
        Level level = levelRepository.findById(id).orElseThrow(LevelNotFoundException::new);

        return level;
    }

}
