package acc.inzynierka.controllers;

import acc.inzynierka.modelsDTO.UserDto;
import acc.inzynierka.payload.request.PasswordChangeRequest;
import acc.inzynierka.payload.request.UserRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.UserService;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(value = {""})
    public ResponseEntity<?> getUser(){
        Long userId = UserUtil.getUser();
        UserDto userDto = userService.getUser(userId);

        return new ResponseEntity<>(
                userDto
                , HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> getUserBySuperAdmin(@PathVariable Long id){
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
    public ResponseEntity<?> editUser(@Valid @RequestBody UserRequest userRequest) {
        Long userId = UserUtil.getUser();
        userService.editUser(userId, userRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano użytkownika"));
    }
    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @PatchMapping(value = {"edit/{id}"})
    public ResponseEntity<?> editUserById(@PathVariable Long userId, @Valid @RequestBody UserRequest userRequest) {
        Long adminId = UserUtil.getUser();
        userService.editUserByAdmin(userId, adminId, userRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zedytowano użytkownika"));
    }

    public ResponseEntity editPassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest){
        Long userId = UserUtil.getUser();
        userService.editPassword(userId, passwordChangeRequest);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie zmieniono hasło"));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<?> deleteUser() {
        Long userId = UserUtil.getUser();
        userService.deleteUser(userId);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto użytkownika"));
    }
    @PreAuthorize(value = "hasRole('ROLE_SUPERADMIN')")
    @DeleteMapping(value = { "delete/{id}"})
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto użytkownika"));
    }
}
