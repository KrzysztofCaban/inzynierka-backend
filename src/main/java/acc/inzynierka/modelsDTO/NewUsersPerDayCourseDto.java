package acc.inzynierka.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NewUsersPerDayCourseDto {
    private List<newUsersPerDay> newUsersPerDay;
    private Timestamp CreationDate;
}
