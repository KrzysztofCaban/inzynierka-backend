package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.CourseDto;
import lombok.Data;

@Data
public class CourseResponse {
    private CourseDto course;
    private String message;
}
