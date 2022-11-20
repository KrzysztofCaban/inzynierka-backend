package acc.inzynierka.services;

import acc.inzynierka.exception.user.UserNotFoundException;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.UserDto;
import acc.inzynierka.payload.request.UserRequest;
import acc.inzynierka.repository.UserRepository;
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
    PasswordEncoder encoder;


    public UserDto getUser(Long id){
        User user = findById(id);

        UserDto userDto = (UserDto) ObjectMapperUtil.mapToDTOSingle(user, UserDto.class);
        userDto.setRoles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));

        return userDto;
    }

    public List<UserDto> getCreators(){
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
                .filter(user -> user.getRoles().contains(ERole.ROLE_ADMIN)).
                collect(Collectors.toList());

        return userDtoList;
    }

    public void editUser(Long id, UserRequest userRequest) {
        User user = findById(id);

        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setActive(userRequest.isActive());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

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
}
