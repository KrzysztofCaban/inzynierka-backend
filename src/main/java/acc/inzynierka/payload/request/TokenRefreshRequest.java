package acc.inzynierka.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank(message = "Nie wprowadzono tokenu")
    private String refreshToken;
}
