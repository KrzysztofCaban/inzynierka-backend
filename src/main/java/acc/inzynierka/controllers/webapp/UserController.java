package acc.inzynierka.controllers.webapp;

import acc.inzynierka.modelsDTO.webapp.UserDto;
import acc.inzynierka.payload.request.UserRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.webapp.UserService;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(value = {""})
    public ResponseEntity<?> getUser() {
        Long userId = UserUtil.getUser();
        UserDto userDto = userService.getUser(userId);

        return new ResponseEntity<>(
                userDto
                , HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> getUserBySuperAdmin(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);

        return new ResponseEntity<>(
                userDto
                , HttpStatus.OK);
    }

    @GetMapping(value = "creators")
    public ResponseEntity<?> getCreators(){
        List<UserDto> creatorsList = userService.getCreators();

        return new ResponseEntity<>(
                creatorsList
                , HttpStatus.OK);
    }

    @PatchMapping(value = "edit")
    public ResponseEntity<?> editUser(UserRequest userRequest) {
        Long userId = UserUtil.getUser();
        userService.editUser(userId, userRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano użytkownika"));
    }

    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @PatchMapping(value = {"edit/{id}"})
    public ResponseEntity<?> editUserById(@PathVariable Long id, UserRequest userRequest) {
        userService.editUser(id, userRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano użytkownika"));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<?> deleteUser() {
        Long userId = UserUtil.getUser();
        userService.deleteUser(userId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto użytkownika"));
    }

    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping(value = {"delete/{id}"})
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto użytkownika"));
    }
}
