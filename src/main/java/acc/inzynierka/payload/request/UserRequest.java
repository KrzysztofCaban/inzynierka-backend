package acc.inzynierka.payload.request;

import acc.inzynierka.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRequest implements Serializable {
    @NotEmpty(message = "Hasło nie może być pusta")
    private String password;
    @NotEmpty(message = "Status nie może być pusty")
    private boolean isActive;
    @NotEmpty(message = "Imie nie może być pusta")
    private String firstName;
    @NotEmpty(message = "Nazwisko nie może być puste")
    private String lastName;
}