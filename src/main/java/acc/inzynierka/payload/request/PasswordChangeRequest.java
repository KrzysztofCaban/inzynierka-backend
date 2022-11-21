package acc.inzynierka.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordChangeRequest {
    @NotEmpty(message = "Stare hasło nie może być pusta")
    private String oldPassword;
    @NotEmpty(message = "Nowe hasło nie może być pusta")
    private String newPassword;
}
