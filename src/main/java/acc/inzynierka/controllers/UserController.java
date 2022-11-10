package acc.inzynierka.controllers;

import acc.inzynierka.payload.request.UserRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.UserService;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"edit", "edit/{id}"})
    public ResponseEntity<?> editUser(@PathVariable Optional<Long> id, UserRequest userRequest) {
        Long userId = id.orElseGet(UserUtil::getUser);
        userService.editUser(userId, userRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano użytkownika"));
    }

    @GetMapping(value = {"delete", "delete/{id}"})
    public ResponseEntity<?> deleteUser(@PathVariable Optional<Long> id) {
        Long userId = id.orElseGet(UserUtil::getUser);
        userService.deleteUser(userId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto użytkownika"));
    }
}
