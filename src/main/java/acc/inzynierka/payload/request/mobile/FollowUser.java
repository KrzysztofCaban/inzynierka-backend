package acc.inzynierka.payload.request.mobile;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FollowUser {
    @NotEmpty(message = "Nazwa użytkownika do śledzenia nie może być pusta")
    private String login;
}
