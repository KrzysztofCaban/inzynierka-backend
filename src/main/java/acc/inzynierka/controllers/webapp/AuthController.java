package acc.inzynierka.controllers.webapp;

import acc.inzynierka.exception.TokenRefreshException;
import acc.inzynierka.models.RefreshToken;
import acc.inzynierka.models.Role;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.payload.request.ForgotPassword;
import acc.inzynierka.payload.request.LoginRequest;
import acc.inzynierka.payload.request.SignupRequest;
import acc.inzynierka.payload.request.TokenRefreshRequest;
import acc.inzynierka.payload.response.JwtResponse;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.payload.response.TokenRefreshResponse;
import acc.inzynierka.repository.RoleRepository;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.security.jwt.JwtUtils;
import acc.inzynierka.security.services.RefreshTokenService;
import acc.inzynierka.security.services.UserDetailsImpl;
import acc.inzynierka.services.EmailSender.EmailService;
import acc.inzynierka.services.webapp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        refreshTokenService.deleteByUserId(userDetails.getId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getLogin(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Błąd: Login jest już w użyciu!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Błąd: Email jest już w użyciu!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getLogin(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            //assigning a default role - user role
            roles.add(roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Błąd. Nie znaleziono roli Użytkownik.")));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_CREATOR":
                        //assigning a creator role
                        roles.add(roleRepository.findByName(ERole.ROLE_CREATOR)
                                .orElseThrow(() -> new RuntimeException("Błąd. Nie znaleziono roli Twórca.")));

                        //assigning a user role
                        roles.add(roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Błąd. Nie znaleziono roli Użytkownik.")));
                        break;
                    case "ROLE_ADMIN":
                        //assigning an admin role
                        roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Błąd. Nie znaleziono roli Administrator.")));

                        //assigning a creator role
                        roles.add(roleRepository.findByName(ERole.ROLE_CREATOR)
                                .orElseThrow(() -> new RuntimeException("Błąd. Nie znaleziono roli Twórca.")));

                        //assigning a user role
                        roles.add(roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Błąd. Nie znaleziono roli Użytkownik.")));
                        break;
                    default:
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        emailService.send(signUpRequest.getEmail(),"Witaj w NABO", "Witaj " + signUpRequest.getLogin() +
                "\n\nDziękujemy za korzystanie z naszej aplikacji. Mamy nadzieję, że nauka języka angielskiego będzie przyjemna i efektywna." +
                "\n\n\n\nPozdrawiamy," +
                "\nZespół NABO");

        return ResponseEntity.ok(new MessageResponse("Zarejestrowano pomyślnie!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getLogin());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Nie odnaleziono Refresh Tokena!"));
    }

    @PostMapping("/signout")
    @PreAuthorize(value = "hasRole('ROLE_CREATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Wylogowano pomyślnie"));
    }

    @PostMapping(value = "forgotPassword")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword) {
        userService.forgotPassword(forgotPassword.getEmail());

        return ResponseEntity.ok().body(new MessageResponse("Pomyślnie wysłano link do resetowania hasła"));
    }

}
