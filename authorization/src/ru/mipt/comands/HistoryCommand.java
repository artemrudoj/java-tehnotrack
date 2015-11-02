package ru.mipt.comands;

import ru.mipt.session.Session;

/**
 * Created by artem on 19.10.15.
 */
public class HistoryCommand implements Command {
    @Override
    public ReturnCode execute(Session session, String[] args) {
        if (!session.isSeesionExist())
            return new ReturnCode(ReturnCode.NO_CURRENT_SESSION);

        switch (args.length) {
            case 2:
                try {
                    int N = Integer.parseInt(args[1]);
                    for (String str : session.getHistoryStorage().returnMessage(N)) {
                        if (str == null) {
                            return new ReturnCode(ReturnCode.SUCCESS, "Message did not find");
                        }
                        return new ReturnCode(ReturnCode.SUCCESS, str);
                    }
                }
                catch (NumberFormatException e) {
                    return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
                }
                default:
                    return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        }
    }
}

