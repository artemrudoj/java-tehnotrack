package ru.mipt.comands;

import ru.mipt.session.Session;
import ru.mipt.session.User;

/**
 * Created by artem on 18.10.15.
 */
public class UserCommand implements Command {
    @Override
    public int execute(Session session, String[] args) {
        if (!session.isSeesionExist())
            return ReturnCode.SESSION_ALREADY_HAVE_USER;

        switch (args.length) {
            case 2:
                session.getSessionUser().setNick(args[1]);
                return ReturnCode.SUCCESS;
        }
        return ReturnCode.INCORRECT_ARGUMENTS;
    }
}
