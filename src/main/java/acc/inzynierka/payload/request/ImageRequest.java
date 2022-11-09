package acc.inzynierka.payload.request;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ImageRequest {
    @NotNull(message = "Zdjęcie nie może być puste")
    private MultipartFile image;
    @NotEmpty(message = "Nazwa zdjecia nie moze byc pusta")
    private String name;
    @NotEmpty(message = "Nazwa kategorii nie może być pusta")
    private String categoryName;
}
