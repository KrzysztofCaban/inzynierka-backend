package acc.inzynierka.payload.response.mobile;

import acc.inzynierka.modelsDTO.mobileapp.LevelResult;
import lombok.Data;

import java.util.List;

@Data
public class UserCourseResults {
    private String courseName;
    private String authorName;
    private List<LevelResult> results;
}
