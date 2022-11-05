package acc.inzynierka.controllers;

import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.BlobStorage.BlobStorageService;
import acc.inzynierka.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    ImageService imageService;

    @PostMapping("uploadImage")
    public ResponseEntity<?> writeBlobFile(@RequestParam(value = "image", required = false) MultipartFile file) throws IOException {
        return new ResponseEntity<>(
                new MessageResponse(blobStorageService.uploadPicture(file).toURL().toString()),
                HttpStatus.OK);
    }

    @GetMapping("{categoryName}")
    public ResponseEntity<?> getAllImagesByCategory(@PathVariable String categoryName){
        return new ResponseEntity<>(
            imageService.getAllImages(categoryName),
            HttpStatus.OK
        );
    }
}
