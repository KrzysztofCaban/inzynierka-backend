package acc.inzynierka.controllers.mobileapp;

import acc.inzynierka.payload.request.mobile.FollowUser;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.services.mobileapp.FollowedMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mobile/follow")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class FollowedMobileController {
    @Autowired
    FollowedMobileService followedMobileService;


    @GetMapping(value = "allUsers")
    public ResponseEntity<?> getAllUsersLogins() {
        return new ResponseEntity<>(
                followedMobileService.getAllUsersLogins(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = {"allFollowed"})
    public ResponseEntity<?> getFollowed() {

        return new ResponseEntity<>(
                followedMobileService.getFollowedUsers(),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "follow")
    public ResponseEntity<?> followUser(@RequestBody FollowUser followUser) {

        followedMobileService.followUser(followUser.getLogin());

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie dodano użytkownika do obserwowanych"));
    }

    @DeleteMapping(value = "unfollow")
    public ResponseEntity<?> unFollowUser(@RequestBody FollowUser followUser) {
        followedMobileService.unFollowUser(followUser.getLogin());

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie usunięto użytkownika z obserwowanych"));
    }

    @GetMapping(value = "followedUsersResults")
    public ResponseEntity<?> followedUsersResults() {
        return new ResponseEntity<>(
                followedMobileService.followedUsersResults(),
                HttpStatus.OK
        );
    }
}
