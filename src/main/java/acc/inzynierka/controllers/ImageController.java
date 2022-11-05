package acc.inzynierka.controllers;

import acc.inzynierka.payload.request.ImageRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.BlobStorage.BlobStorageService;
import acc.inzynierka.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
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



    @Autowired
    ImageService imageService;

    @PostMapping(value = "uploadImage", consumes = {MediaType.APPLICATION_JSON_VALUE,
                                                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> writeBlobFile(@RequestPart("data") ImageRequest imageRequest, @RequestPart("image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(
                imageService.uploadImage(imageRequest,image),
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
