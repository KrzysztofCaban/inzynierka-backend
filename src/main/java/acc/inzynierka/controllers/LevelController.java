package acc.inzynierka.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/course/{courseID}/level/")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class LevelController {

}
