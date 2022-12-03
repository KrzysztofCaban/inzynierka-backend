package acc.inzynierka.modelsDTO.mobileapp;

import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * A DTO for the {@link User} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowedResultsDto implements Serializable {
    private String login;
    private int result;
}