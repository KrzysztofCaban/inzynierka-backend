package acc.inzynierka.modelsDTO;

import lombok.Data;

@Data
public class CourseStatsDto {
    private Long courseId;
    private String courseName;
    private Integer usersInCourse;
    private Double avgScoreInCourse;

    public CourseStatsDto(Long courseId, String courseName, Integer usersInCourse, Double avgScoreInCourse) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.usersInCourse = usersInCourse;
        this.avgScoreInCourse = avgScoreInCourse;
    }
}
