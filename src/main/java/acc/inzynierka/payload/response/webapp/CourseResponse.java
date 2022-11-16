package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.CourseDto;
import lombok.Data;

@Data
public class CourseResponse {
    private CourseDto course;
    private String message;
}
