package acc.inzynierka.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "Długość loginu powinna wynosić pomiędzy 3 a 20 znaków")
    private String login;

    @NotBlank
    @Size(min = 6, max = 40, message = "Długość hasła powinna wynosić pomiędzy 6 a 40 znaków")
    private String password;
}
