package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.followed.UserIsAlreadyFollowedException;
import acc.inzynierka.exception.followed.UserIsNotFollowedException;
import acc.inzynierka.models.Followed;
import acc.inzynierka.models.User;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.modelsDTO.mobileapp.FollowedResultsDto;
import acc.inzynierka.modelsDTO.mobileapp.FollowedUserDto;
import acc.inzynierka.modelsDTO.mobileapp.UserToFollowDto;
import acc.inzynierka.modelsDTO.webapp.UserDto;
import acc.inzynierka.repository.FollowedRepository;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowedMobileService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private FollowedRepository followedRepository;
    @Autowired
    private UserMobileService userMobileService;

    public List<UserToFollowDto> getAllUsersLogins() {
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

        User currentUser = userMobileService.findById(UserUtil.getUser());
        List<UserToFollowDto> loginsList = userDtoList.stream()
                .filter(user -> user.getRoles().contains(ERole.ROLE_USER))
                .filter(user -> !user.getLogin().equals(currentUser.getLogin()))
                .map(user ->
                        new UserToFollowDto(
                                user.getLogin(),
                                followedRepository.existsByUser_IdAndFollowedUser_Login(currentUser.getId(), user.getLogin())
                        ))
                .collect(Collectors.toList());

        return loginsList;
    }

    public List<FollowedUserDto> getFollowedUsers() {
        Long userId = UserUtil.getUser();
        User user = userMobileService.findById(userId);

        List<User> followedUsers = user.getFollowed().stream()
                .map(followed -> followed.getFollowedUser())
                .collect(Collectors.toList());

        return ObjectMapperUtil.mapToDTO(followedUsers, FollowedUserDto.class);
    }

    public void followUser(String loginToFollow) {
        Long userId = UserUtil.getUser();
        if (followedRepository.existsByUser_IdAndFollowedUser_Login(userId, loginToFollow))
            throw new UserIsAlreadyFollowedException();

        User user = userMobileService.findById(userId);
        User userToFollow = userMobileService.findByLogin(loginToFollow);

        Followed newFollow = new Followed();
        newFollow.setUser(user);
        newFollow.setFollowedUser(userToFollow);

        followedRepository.save(newFollow);
    }

    public void unFollowUser(String loginToUnFollow) {
        Long userId = UserUtil.getUser();
        if (!followedRepository.existsByUser_IdAndFollowedUser_Login(userId, loginToUnFollow))
            throw new UserIsNotFollowedException();

        User user = userMobileService.findById(userId);
        User userToUnfollow = userMobileService.findByLogin(loginToUnFollow);

        Followed followed = followedRepository.findByUser_IdAndFollowedUser_Login(userId, loginToUnFollow);

        followedRepository.delete(followed);
    }

    public List<FollowedResultsDto> followedUsersResults() {
        Long userId = UserUtil.getUser();

        User user = userMobileService.findById(userId);
        List<Followed> followedList = user.getFollowed();
        List<FollowedResultsDto> followedResultsDtoList = followedList.stream()
                .map(followed -> {
                    User followedUser = followed.getFollowedUser();
                    int result = followedUser.getResults().stream()
                            .mapToInt(userResult -> userResult.getValue()).sum();

                    return new FollowedResultsDto(followedUser.getLogin(), result);
                }).collect(Collectors.toList());

        followedResultsDtoList.add(new FollowedResultsDto(user.getLogin(), user.getResults().stream().mapToInt(result -> result.getValue()).sum()));
        followedResultsDtoList = followedResultsDtoList.stream().sorted(Comparator.comparing(FollowedResultsDto::getResult).reversed()).collect(Collectors.toList());
        return followedResultsDtoList;
    }


}
