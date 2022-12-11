package acc.inzynierka.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordRequest {

    @NotEmpty(message = "Hasło nie może być pusta")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Hasło nie spełnia wymogów")
    @Size(min = 6, max = 40, message = "Długość hasła powinna wynosić pomiędzy 6 a 40 znaków")

    private String password;
}
