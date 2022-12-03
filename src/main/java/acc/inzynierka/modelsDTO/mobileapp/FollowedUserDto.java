package acc.inzynierka.modelsDTO.mobileapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link acc.inzynierka.models.User} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowedUserDto implements Serializable {
    private String login;
}