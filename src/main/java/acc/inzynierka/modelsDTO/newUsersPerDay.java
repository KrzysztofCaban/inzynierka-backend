package acc.inzynierka.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class newUsersPerDay {
    private String date;
    private Integer users;
}
