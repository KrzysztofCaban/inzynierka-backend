package acc.inzynierka.payload.response.webapp;

import acc.inzynierka.modelsDTO.webapp.ImageDto;
import lombok.Data;

@Data
public class ImageResponse {
    private ImageDto image;
    private String message;
}
