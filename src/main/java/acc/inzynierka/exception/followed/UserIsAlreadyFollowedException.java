package acc.inzynierka.exception.followed;

import acc.inzynierka.exception.ApiRuntimeException;

public class UserIsAlreadyFollowedException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UserIsAlreadyFollowedException() {
        super("Juz obserwujesz tego użytkownika");
    }
}
