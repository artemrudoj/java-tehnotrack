package ru.mipt.comands;


import ru.mipt.authorization.AuthorizationService;
import ru.mipt.message.Message;
import ru.mipt.messagestore.BasedOnListStorage;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;
import ru.mipt.session.SessionStorage;
import ru.mipt.session.User;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {

    private AuthorizationService service;
    private SessionStorage sessionStorage;
    private long possibleSessionId;


    public LoginCommand(AuthorizationService service, SessionStorage sessionStorage) {
        this.service = service;
        this.sessionStorage = sessionStorage;
    }

    @Override
    public ReturnCode execute(Session session, Message message) {
        assert(service != null);
        if (session != null)
            return new ReturnCode(ReturnCode.SESSION_ALREADY_HAVE_USER);
        String args[] = message.getMessage().split(" ");
        switch (args.length) {
            case 3:
                User user = service.login(args[1], args[2]);
                if (user == null)
                    return new ReturnCode(ReturnCode.USER_NOT_EXIST);
                session = new Session(new BasedOnListStorage());
                session.setSessionUser(user);
                sessionStorage.addSession(session, possibleSessionId);
                return new ReturnCode(ReturnCode.SUCCESS, args[1] + " " + args[2] + " " + Long.toString(user.getUserId()));
            default:
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }

    public long getPossibleSessionId() {
        return possibleSessionId;
    }

    public void setPossibleSessionId(long possibleSessionId) {
        this.possibleSessionId = possibleSessionId;
    }
}
