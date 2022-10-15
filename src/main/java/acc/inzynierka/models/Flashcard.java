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
@Table(name = "flashcard")
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flashcard_id",nullable = false)
    private Long id;

    @Column(name = "exp_original", nullable = false)
    private String expOriginal;

    @Column(name = "exp_translation", nullable = false)
    private String expTranslation;

    @Column(name = "exp_description", nullable = false)
    private String expDescription;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

}
