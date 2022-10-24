package acc.inzynierka.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<Exercise> exercises;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<TestQuestion> testQuestions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<Flashcard> flashcards;
}

