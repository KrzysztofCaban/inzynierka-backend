package acc.inzynierka.services.mobileapp;

import acc.inzynierka.models.Result;
import acc.inzynierka.models.User;
import acc.inzynierka.modelsDTO.mobileapp.LevelResult;
import acc.inzynierka.payload.response.mobile.UserCourseResults;
import acc.inzynierka.services.webapp.UserService;
import acc.inzynierka.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StatsMobileService {


    private final UserService userService;

    public StatsMobileService(UserService userService) {
        this.userService = userService;
    }

    public Object getCourseResults() {
        Long userId = UserUtil.getUser();
        User user = userService.findById(userId);

        List<UserCourseResults> userCourseResultsList = user.getCourses().stream().map(userCourse -> {
            UserCourseResults userCourseResults = new UserCourseResults();
            userCourseResults.setCourseName(userCourse.getCourse().getName());
            userCourseResults.setAuthorName(userCourse.getCourse().getAuthor().getLogin());
            userCourseResults.setResults(userCourse.getCourse().getLevels().stream().map(level -> {
                        LevelResult levelResult = new LevelResult();
                        levelResult.setLevelName(level.getName());
                        levelResult.setLevelDifficulty(level.getDifficulty());
                        Result resultt = new Result();
                        resultt.setValue(-1);
                        levelResult.setLevelScore(level.getLevelResults().stream().filter(result -> result.getUser().getId().equals(userId)).findFirst().orElse(resultt).getValue());
                        return levelResult;
                    })
                    .filter(out -> out.getLevelScore() != -1)
                    .sorted(Comparator.comparingInt(LevelResult::getLevelDifficulty))
                    .toList());
            return userCourseResults;
        }).toList();

        return userCourseResultsList;
    }
}
