package acc.inzynierka.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseStatsAllDto {
    private Long courseId;
    private String author;
    private String courseName;
    private Integer usersInCourse;
    private Double avgScoreInCourse;


}
