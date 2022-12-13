package acc.inzynierka.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "difficulty", nullable = false)
    private int difficulty;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<Result> levelResults;

    public Course getCourse() {
        return course;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<Exercise> exercises;

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<TestQuestion> testQuestions;

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<TestQuestion> getTestQuestions() {
        return testQuestions;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<Flashcard> flashcards;

    public void setTestQuestions(List<TestQuestion> testQuestions) {
        this.testQuestions = testQuestions;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public List<Result> getLevelResults() {
        return levelResults;
    }

    public void setLevelResults(List<Result> levelResults) {
        this.levelResults = levelResults;
    }

}

