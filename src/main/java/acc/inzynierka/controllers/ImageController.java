package acc.inzynierka.controllers;

import acc.inzynierka.payload.request.ImageRequest;
import acc.inzynierka.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/image")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
@RequiredArgsConstructor
public class ImageController {


    @Autowired
    ImageService imageService;

    @PostMapping(value = "uploadImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @RequestPart("data") ImageRequest imageRequest, @RequestPart("image") MultipartFile image
    public ResponseEntity<?> writeBlobFile(@ModelAttribute ImageRequest imageRequest) throws IOException {
        return new ResponseEntity<>(
                imageService.uploadImage(imageRequest, imageRequest.getImage()),
                HttpStatus.OK);
    }

    @GetMapping("{categoryName}")
    public ResponseEntity<?> getAllImagesByCategory(@PathVariable String categoryName) {
        return new ResponseEntity<>(
                imageService.getAllImages(categoryName),
                HttpStatus.OK
        );
    }
}
