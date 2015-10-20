package ru.mipt.comands;

import ru.mipt.session.Session;

import java.util.ArrayList;

/**
 * Created by artem on 18.10.15.
 */
public class FindCommand implements Command {
    @Override
    public int execute(Session session, String[] args) {
        if (!session.isSeesionExist())
            return ReturnCode.USER_NOT_EXIST;
        switch (args.length) {
            case 2:
                ArrayList<String> finded = session.getHistoryStorage().findMessage(args[1]);
                if (finded.isEmpty())
                    return ReturnCode.COMMAND_NOT_FOUNDED;
                for (String str : finded)
                    System.out.println(str);
                return ReturnCode.SUCCESS;
        }
        return ReturnCode.INCORRECT_ARGUMENTS;
    }
}
