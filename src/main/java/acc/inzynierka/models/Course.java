package acc.inzynierka.models;

import acc.inzynierka.modelsDTO.CourseStatsAllDto;
import acc.inzynierka.modelsDTO.CourseStatsDto;
import acc.inzynierka.modelsDTO.newUsersPerDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")

@NamedNativeQuery(name = "getCoursesStatsForOneAuthor",
        query = "SELECT c.id as courseId ,c.name as courseName, COUNT(uc.user_id) as usersInCourse ,IFNULL(avgr.avgrr,0) as avgScoreInCourse " +
                "FROM Course c " +
                "LEFT JOIN  user_courses uc ON c.id = uc.course_id " +
                "LEFT JOIN " +
                "(SELECT c.id as id, avg(r.value) as avgrr " +
                "FROM Course c " +
                "LEFT JOIN  level l on c.id = l.course_id " +
                "LEFT JOIN user_result r on l.id = r.level_id " +
                "WHERE c.author_id = :adminID " +
                "GROUP BY c.name ) as avgr on c.id = avgr.id " +
                "WHERE c.author_id = :adminID " +
                "GROUP BY c.name ",
        resultSetMapping = "modelsDTO.CourseStatsDto")
@SqlResultSetMapping(name = "modelsDTO.CourseStatsDto",
        classes = @ConstructorResult(targetClass = CourseStatsDto.class,
                columns = {@ColumnResult(name = "courseId", type = Long.class),
                        @ColumnResult(name = "courseName", type = String.class),
                        @ColumnResult(name = "usersInCourse", type = Integer.class),
                        @ColumnResult(name = "avgScoreInCourse", type = Double.class)}))
@NamedNativeQuery(name = "getCoursesStatsForAllAuthors",
        query = "SELECT c.id as courseId, u.login as author ,c.name as courseName, COUNT(uc.user_id) as usersInCourse ,IFNULL(avgr.avgrr,0) as avgScoreInCourse " +
                "FROM Course c " +
                "LEFT JOIN  user_courses uc ON c.id = uc.course_id " +
                "LEFT JOIN " +
                "(SELECT c.id as id, avg(r.value) as avgrr " +
                "FROM Course c " +
                "LEFT JOIN  level l on c.id = l.course_id " +
                "LEFT JOIN user_result r on l.id = r.level_id " +
                "GROUP BY c.name ) as avgr on c.id = avgr.id " +
                "left join user u on u.id = c.author_id " +
                "GROUP BY c.name ",
        resultSetMapping = "modelsDTO.CourseStatsAllDto")
@SqlResultSetMapping(name = "modelsDTO.CourseStatsAllDto",
        classes = @ConstructorResult(targetClass = CourseStatsAllDto.class,
                columns = {@ColumnResult(name = "courseId", type = Long.class),
                        @ColumnResult(name = "author", type = String.class),
                        @ColumnResult(name = "courseName", type = String.class),
                        @ColumnResult(name = "usersInCourse", type = Integer.class),
                        @ColumnResult(name = "avgScoreInCourse", type = Double.class)}))
@NamedNativeQuery(name = "newUsersPerDay",
        query = "SELECT " +
                "DATE_FORMAT(uc.join_date, '%Y-%m-%d') as date, COUNT(uc.course_id) as users " +
                "FROM course c " +
                "LEFT JOIN user_courses uc on c.id = uc.course_id " +
                "WHERE c.id = :courseId "
                + "GROUP BY  DATE_FORMAT(uc.join_date, '%Y-%m-%d')",
        resultSetMapping = "modelsDTO.newUsersPerDay")
@SqlResultSetMapping(name = "modelsDTO.newUsersPerDay",
        classes = @ConstructorResult(targetClass = newUsersPerDay.class,
                columns = {
                        @ColumnResult(name = "date", type = String.class),
                        @ColumnResult(name = "users", type = Integer.class)}))

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Column(name = "modified", nullable = false)
    private Timestamp modified;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<UserCourse> courseUsers;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;

    @JoinColumn(name = "status_id", nullable = false)
    @ManyToOne(optional = false)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Level> levels;
}
