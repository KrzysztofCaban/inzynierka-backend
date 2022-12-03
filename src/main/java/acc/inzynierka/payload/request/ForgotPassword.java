package acc.inzynierka.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ForgotPassword {

    @NotBlank
    private String email;
}
