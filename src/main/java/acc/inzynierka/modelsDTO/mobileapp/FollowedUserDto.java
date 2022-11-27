package acc.inzynierka.modelsDTO.mobileapp;

import lombok.*;

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