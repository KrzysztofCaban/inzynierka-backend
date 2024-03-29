package acc.inzynierka.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;

}
