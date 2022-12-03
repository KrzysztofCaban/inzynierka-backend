package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.services.mobileapp.StatsMobileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/stats")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class StatsMobileController {


    private final StatsMobileService statsMobileService;

    public StatsMobileController(StatsMobileService statsMobileService) {
        this.statsMobileService = statsMobileService;
    }

    @GetMapping()
    public ResponseEntity<?> getResults() {

        return new ResponseEntity<>(
                statsMobileService.getCourseResults(),
                HttpStatus.OK
        );
    }
}
