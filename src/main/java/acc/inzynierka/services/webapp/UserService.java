package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.user.PasswordNotCorrectException;
import acc.inzynierka.exception.user.UserNotFoundException;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.webapp.UserDto;
import acc.inzynierka.payload.request.PasswordChangeRequest;
import acc.inzynierka.payload.request.PasswordRequest;
import acc.inzynierka.payload.request.UserRequest;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.security.jwt.JwtUtils;
import acc.inzynierka.services.EmailSender.EmailService;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder encoder;


    public UserDto getUser(Long id) {
        User user = findById(id);

        UserDto userDto = (UserDto) ObjectMapperUtil.mapToDTOSingle(user, UserDto.class);
        userDto.setRoles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));

        return userDto;
    }

    public List<UserDto> getCreators() {
        List<User> userList = userRepository.findAll();

        List<UserDto> userDtoList = userList.stream()
                .map(user ->
                        new UserDto(user.getId(),
                                user.getLogin(),
                                user.getEmail(),
                                user.isActive(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getCreationDate(),
                                user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                ).collect(Collectors.toList());

        userDtoList = userDtoList.stream()
                .filter(user -> user.getRoles().contains(ERole.ROLE_CREATOR)).
                collect(Collectors.toList());

        return userDtoList;
    }

    public void editUser(Long id, UserRequest userRequest) {
        User user = findById(id);

        checkPasswords(user.getPassword(), userRequest.getPassword());

        user.setActive(userRequest.isActive());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        userRepository.save(user);
    }

    public void forgotPassword(String email) {
        User user = findByEmail(email);

        String jwt = jwtUtils.generateJwtTokenEmail(user);

        String appUrl = "http://localhost:4200/reset?token=";

        String link = appUrl + jwt;
        emailService.send(email, "Reset hasła", "Witaj " + user.getLogin() + ",\n\nKliknij aby zresetować swoje hasło:\n" +
                link);
    }

    public void resetPassword(PasswordRequest passwordRequest) {
        Long userId = UserUtil.getUser();

        User user = findById(userId);

        user.setPassword(encoder.encode(passwordRequest.getPassword()));

        userRepository.save(user);
    }

    public void editUserByAdmin(Long userId, Long adminId, UserRequest userRequest) {
        User admin = findById(adminId);
        checkPasswords(admin.getPassword(), userRequest.getPassword());

        User user = findById(userId);
        user.setActive(userRequest.isActive());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        userRepository.save(user);
    }

    public void editPassword(Long id, PasswordChangeRequest passwordChangeRequest) {
        User user = findById(id);

        checkPasswords(passwordChangeRequest.getOldPassword(), user.getPassword());
        user.setPassword(encoder.encode(passwordChangeRequest.getNewPassword()));

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findById(id);

        userRepository.delete(user);
    }


    public User findById(long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user;
    }

    public void checkPasswords(String oldPassword, String newPassword) {
        if (!encoder.matches(newPassword, oldPassword))
            throw new PasswordNotCorrectException();
    }
}
