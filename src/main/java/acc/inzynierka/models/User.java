package acc.inzynierka.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "Is_active", nullable = false)
    private boolean isActive;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created", nullable = false) //tu będzie zmiana na Created
    private Timestamp CreationDate;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Result> results;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Followed> followed;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserCourse> courses;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Collection<PotentialCategory> PotentialCategories;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.CreationDate = Timestamp.from(Instant.now());
    }

}
