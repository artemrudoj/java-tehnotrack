package ru.mipt.comands;

import ru.mipt.authorization.AuthorizationService;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;
import ru.mipt.session.SessionStorage;


public class RegistrationCommand implements Command {


    private AuthorizationService service;

    public RegistrationCommand(AuthorizationService service) {
        this.service = service;
    }
    @Override
    public ReturnCode execute(Session session, Message message) {
        assert(service != null);
        if (session != null)
            return new ReturnCode(ReturnCode.SESSION_ALREADY_HAVE_USER);
        String args[] = message.getMessage().split(" ");
        switch (args.length) {
            case 3:
                Long userId = service.createUser(args[1], args[2]);
                if (userId == null)
                    return new ReturnCode(ReturnCode.USER_ALREADY_EXIST);
                return new ReturnCode(ReturnCode.SUCCESS);
            default:
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }
}
