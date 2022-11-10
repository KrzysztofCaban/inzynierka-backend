package acc.inzynierka.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
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

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;

    @JoinColumn(name = "status_id", nullable = false)
    @ManyToOne(optional = false)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Level> levels;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<UserCourse> courseUsers;
}
