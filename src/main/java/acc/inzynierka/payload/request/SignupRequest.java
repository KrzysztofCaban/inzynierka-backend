package acc.inzynierka.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "Długość loginu powinna wynosić pomiędzy 3 a 20 znaków")
    private String login;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40, message = "Długość hasła powinna wynosić pomiędzy 6 a 40 znaków")
    private String password;

}
