package acc.inzynierka.modelsDTO;

import acc.inzynierka.models.Role;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import lombok.*;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private Long Id;
    private String login;
    private String email;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private Timestamp CreationDate;
    private List<ERole> roles;
}