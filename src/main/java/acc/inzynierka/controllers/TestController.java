package acc.inzynierka.controllers;

import acc.inzynierka.security.services.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("test")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN ')")
    public String test(@AuthenticationPrincipal UserDetailsImpl user) {
        return user.getLogin();
    }
}
