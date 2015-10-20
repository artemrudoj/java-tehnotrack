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
    public int execute(Session session, String[] args) {
        System.out.println("Executing login");

        if (session.isSeesionExist())
            return ReturnCode.SESSION_ALREADY_HAVE_USER;

        switch (args.length) {
            case 1:
                try {
                    User user = service.createUser();
                    session.setSessionUser(user);
                    return ReturnCode.SUCCESS;
                }
                catch (NullPointerException e) {
                    return ReturnCode.USER_ALREADY_EXIST;
                }
            case 2:
                try {
                    User user = service.login(args[1]);
                    session.setSessionUser(user);
                    return ReturnCode.SUCCESS;
                }
                catch (NullPointerException e) {
                    return ReturnCode.USER_NOT_EXIST;
                }

        }
        return ReturnCode.INCORRECT_ARGUMENTS;
    }
}
