package ru.mipt.comands;

import ru.mipt.authorization.AuthorizationService;
import ru.mipt.hisorystorage.BasedOnListStorage;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;
import ru.mipt.session.SessionStorage;
import ru.mipt.session.User;

/**
 * Created by artem on 27.10.15.
 */
public class RegistrationCommand implements Command {


    private AuthorizationService service;
    private SessionStorage sessionStorage;

    public RegistrationCommand(AuthorizationService service, SessionStorage sessionStorage) {
        this.service = service;
        this.sessionStorage = sessionStorage;
    }
    @Override
    public ReturnCode execute(Session session, String[] args) {
        assert(service != null);
        if (session != null)
            return new ReturnCode(ReturnCode.SESSION_ALREADY_HAVE_USER);
        switch (args.length) {
            case 3:
                User user = service.createUser(args[1], args[2]);
                if (user == null)
                    return new ReturnCode(ReturnCode.USER_NOT_EXIST);
                return new ReturnCode(ReturnCode.SUCCESS);
            default:
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }
}
