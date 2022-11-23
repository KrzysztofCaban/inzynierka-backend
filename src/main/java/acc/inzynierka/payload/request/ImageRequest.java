package acc.inzynierka.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ImageRequest {
    @NotNull(message = "Zdjęcie nie może być puste")
    private MultipartFile image;

    @NotEmpty(message = "Nazwa zdjecia nie moze byc pusta")
    @Size(min = 2, max = 24, message = "Długość nazwy powinna zawierać się pomiędzy 2 a 24 znaków")
    private String name;

    @NotEmpty(message = "Nazwa kategorii nie może być pusta")
    private String categoryName;
}
