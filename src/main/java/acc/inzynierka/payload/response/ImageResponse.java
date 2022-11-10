package acc.inzynierka.payload.response;

import acc.inzynierka.modelsDTO.ImageDto;
import lombok.Data;

@Data
public class ImageResponse {
    private ImageDto image;
    private String message;
}
