package ru.mipt.comands;


import ru.mipt.authorization.AuthorizationService;
import ru.mipt.session.Session;
import ru.mipt.session.User;

import java.util.Scanner;

/**
 * Выполняем авторизацию по этой команде
 */
public class LoginCommand implements Command {

    private AuthorizationService service;

    public LoginCommand(AuthorizationService service) {
        this.service = service;
    }

    @Override
    public ReturnCode execute(Session session, String[] args) {

        if (session.isSeesionExist())
            return new ReturnCode(ReturnCode.SESSION_ALREADY_HAVE_USER);

        switch (args.length) {
            case 3:
                User user = service.login(args[1], args[2]);
                if (user == null)
                    return new ReturnCode(ReturnCode.USER_NOT_EXIST);
                session.setSessionUser(user);
                return new ReturnCode(ReturnCode.SUCCESS);
            default:
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }
}
