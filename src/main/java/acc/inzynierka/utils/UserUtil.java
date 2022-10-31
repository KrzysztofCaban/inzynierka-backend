package acc.inzynierka.utils;

import acc.inzynierka.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {


    public static Long getUser(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
