package ru.mipt.comands;

import ru.mipt.authorization.UserStore;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;
import ru.mipt.session.User;

import javax.jws.soap.SOAPBinding;

/**
 * Created by artem on 18.10.15.
 */
public class UserCommand implements Command {

    UserStore userStore;

    public UserCommand(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public ReturnCode execute(Session session, Message message) {
        if (session == null)
            return new ReturnCode(ReturnCode.NO_CURRENT_SESSION);
        String args[] = message.getMessage().split(" ");
        User user = null;
        if (args[1].equals("info")) {
            if ((args.length != 2) && (args.length != 3))
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            if (args.length == 2) {
                user = session.getSessionUser();
                assert (user != null);
            } else if (args.length == 3) {
                try {
                    Long userId = Long.parseLong(args[2]);
                    user = userStore.findUserById(userId);
                    if (user == null)
                        return new ReturnCode(ReturnCode.USER_NOT_EXIST);
                } catch (NumberFormatException e) {
                    return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
                }
            }
            assert (user != null);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("name = ").append(user.getName()).append(" ");
            stringBuilder.append("nickname = ").append(user.getNick()).append(" ");
            stringBuilder.append("id = ").append(user.getUserId());
            return new ReturnCode(ReturnCode.SUCCESS, stringBuilder.toString());
        } else if (args[1].equals("pass")) {
            if (args.length != 4)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            String oldPassword = args[2];
            String newPassword = args[3];
            user = session.getSessionUser();
            assert (user != null);
            if (oldPassword.equals(user.getPass())){
                user.setPass(newPassword);
                return new ReturnCode(ReturnCode.SUCCESS);
            } else
                return new ReturnCode(ReturnCode.INCORRECT_LOGIN_OR_PASSWORD);
        } else if(args[1].equals("nick")) {
            if (args.length != 3)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            session.getSessionUser().setNick(args[2]);
            return new ReturnCode(ReturnCode.SUCCESS);
        } else
            return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
    }
}
