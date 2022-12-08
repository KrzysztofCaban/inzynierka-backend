package acc.inzynierka.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ForgotPassword {

    @NotBlank(message = "Email nie może być pusty")
    @Email
    private String email;
}
