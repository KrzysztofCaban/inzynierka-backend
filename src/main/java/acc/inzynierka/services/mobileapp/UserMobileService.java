package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.user.UserNotFoundException;
import acc.inzynierka.models.User;
import acc.inzynierka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMobileService {

    @Autowired
    UserRepository userRepository;

    public User findById(long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return user;
    }

    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(UserNotFoundException::new);
        return user;
    }
}
