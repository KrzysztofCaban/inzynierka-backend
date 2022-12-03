package acc.inzynierka.modelsDTO.webapp;

import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * A DTO for the {@link User} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private Long id;
    private String login;
    private String email;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private Timestamp CreationDate;
    private List<ERole> roles;
}