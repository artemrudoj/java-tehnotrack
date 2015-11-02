package ru.mipt.comands;

import ru.mipt.session.Session;

/**
 * Created by artem on 18.10.15.
 */
public class UserCommand implements Command {
    @Override
    public ReturnCode execute(Session session, String[] args) {
        if (!session.isSeesionExist())
            return new ReturnCode(ReturnCode.NO_CURRENT_SESSION);

        switch (args.length) {
            case 2:
                session.getSessionUser().setNick(args[1]);
                return new ReturnCode(ReturnCode.SUCCESS);
            default:
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }
}
