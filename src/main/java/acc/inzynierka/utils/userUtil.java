package acc.inzynierka.utils;

import acc.inzynierka.models.User;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class userUtil {


    public static Long getUser(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
