package ru.mipt.comands;

import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;

import java.util.ArrayList;

/**
 * Created by artem on 18.10.15.
 */
public class FindCommand implements Command {
    @Override
    public ReturnCode execute(Session session, Message message) {
        if (!session.isSeesionExist())
            return new ReturnCode(ReturnCode.NO_CURRENT_SESSION);
        String args[] = message.getMessage().split(" ");
        switch (args.length) {
            case 2:
//                ArrayList<String> finded = session.getMessageStore().findMessage(args[1]);
//                if (finded.isEmpty())
//                    return new ReturnCode(ReturnCode.COMMAND_NOT_FOUNDED);
//                return new ReturnCode(ReturnCode.SUCCESS, finded.toString());
            default:
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }
}
