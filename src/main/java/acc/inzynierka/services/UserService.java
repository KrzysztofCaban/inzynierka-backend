package acc.inzynierka.services;

import acc.inzynierka.exception.user.UserNotFoundException;
import acc.inzynierka.models.User;
import acc.inzynierka.payload.request.UserRequest;
import acc.inzynierka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void editUser(Long id, UserRequest userRequest){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        user.setPassword(userRequest.getPassword());
        user.setActive(userRequest.isActive());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        userRepository.save(user);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }
}
