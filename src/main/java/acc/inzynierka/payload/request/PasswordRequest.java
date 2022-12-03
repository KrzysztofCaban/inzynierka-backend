package acc.inzynierka.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PasswordRequest {

    @NotEmpty(message = "Hasło nie może być pusta")
    private String password;
}
