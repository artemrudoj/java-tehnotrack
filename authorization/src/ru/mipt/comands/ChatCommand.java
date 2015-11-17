package ru.mipt.comands;

import ru.mipt.chat.Chat;
import ru.mipt.chat.ChatStorage;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;

import java.util.LinkedList;

/**
 * Created by artem on 16.11.15.
 */
public class ChatCommand implements Command {

    ChatStorage chatStorage;

    public ChatCommand(ChatStorage chatStorage) {
        this.chatStorage = chatStorage;
    }

    @Override
    public ReturnCode execute(Session session, String[] args) {
        if (session == null)
            return new ReturnCode(ReturnCode.NO_CURRENT_SESSION);
        if (args[1] == null)
            return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
        if (args[1].equals("list")) {
            long userId = session.getSessionUser().getUserId();
            LinkedList<Long> chatIds = chatStorage.getChatsForUser(userId);
            if (chatIds == null)
                return new ReturnCode(ReturnCode.SUCCESS, "chats not found");
            else {
                StringBuffer chats = new StringBuffer();
                for (long id : chatIds) {
                    chats.append(id);
                    chats.append(" ");
                }
                return new ReturnCode(ReturnCode.SUCCESS, chats.toString());
            }
        } else if (args[1].equals("create")) {
            //mnimum one id
            if (args[2] == null)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            try {
                Chat chat = new Chat();
                for ( int i = 2; i < args.length; i++) {
                    chat.addParticipant(Long.parseLong(args[i]));
                }
                Long chatId = chatStorage.addChat(chat);
                return new ReturnCode(ReturnCode.SUCCESS, chatId.toString());
            } catch (NumberFormatException e) {
                    return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
                }
        } else if (args[1].equals("send")) {
            if (args[2] == null)
                return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
            return new ReturnCode(ReturnCode.SUCCESS);
        } else
            return new ReturnCode(ReturnCode.INCORRECT_ARGUMENTS);
    }

}
