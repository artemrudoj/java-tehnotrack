package ru.mipt.comands;

import ru.mipt.session.Session;

/**
 * Created by artem on 19.10.15.
 */
public class HistoryCommand implements Command {
    @Override
    public int execute(Session session, String[] args) {
        if (!session.isSeesionExist())
            return ReturnCode.USER_NOT_EXIST;

        switch (args.length) {
            case 2:
                try {
                    int N = Integer.parseInt(args[1]);
                    for(String str : session.getHistoryStorage().returnMessage(N)) {
                        if( str == null )
                            return ReturnCode.SUCCESS;
                        System.out.println(str);
                        return ReturnCode.SUCCESS;
                    }
                }
                catch (NumberFormatException e) {
                    return ReturnCode.INCORRECT_ARGUMENTS;
                }
        }
        return ReturnCode.INCORRECT_ARGUMENTS;
    }
}

