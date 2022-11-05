package acc.inzynierka.controllers;

import acc.inzynierka.services.BlobStorage.BlobStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/image")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
@RequiredArgsConstructor
public class ImageController {

    private final BlobStorageService blobStorageService;

    @PostMapping("uploadImage")
    public String writeBlobFile(@RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        return blobStorageService.uploadPicture(file).toURL().toString();
    }
}
