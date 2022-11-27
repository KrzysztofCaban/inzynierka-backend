package acc.inzynierka.exception.followed;

import acc.inzynierka.exception.ApiRuntimeException;

public class UserIsNotFollowedException extends ApiRuntimeException {
    private static final long serialVersionUID = 1L;

    public UserIsNotFollowedException() {
        super("Nie obserwujesz tego użytkownika");
    }
}
