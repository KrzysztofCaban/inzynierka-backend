package acc.inzynierka.payload.request;

import acc.inzynierka.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 6, max = 40, message = "Długość hasła powinna wynosić pomiędzy 6 a 40 znaków")
    private String password;

    @NotNull(message = "Status nie może być pusty")
    private boolean isActive;

    @NotEmpty(message = "Imie nie może być pusta")
    @Size(min = 3, max = 20, message = "Długość imienia powinna wynosić pomiędzy 3 a 20 znaków")
    private String firstName;

    @NotEmpty(message = "Nazwisko nie może być puste")
    @Size(min = 3, max = 26, message = "Długość imienia powinna wynosić pomiędzy 3 a 26 znaków")
    private String lastName;
}