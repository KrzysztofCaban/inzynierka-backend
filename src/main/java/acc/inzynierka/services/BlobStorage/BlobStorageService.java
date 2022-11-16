package acc.inzynierka.services.BlobStorage;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface BlobStorageService {
    URI uploadPicture(MultipartFile multipartFile);
}
